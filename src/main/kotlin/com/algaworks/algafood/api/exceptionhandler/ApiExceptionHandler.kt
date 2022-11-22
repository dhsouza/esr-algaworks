package com.algaworks.algafood.api.exceptionhandler

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.exceptions.NegocioException
import com.fasterxml.jackson.databind.JsonMappingException.Reference
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.PropertyBindingException
import org.springframework.beans.TypeMismatchException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


private const val MSG_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. " +
        "Tente novamente e se o problema " +
        "persistir, entre em contato com o administrador do sistema."

@ControllerAdvice
class ApiExceptioHandler(
    val messageSource: MessageSource
) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleUncaught(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val problemType = ProblemType.ERRO_DE_SISTEMA
        val detail = MSG_ERRO_GENERICO_USUARIO_FINAL

        // printStackTrace temporário até implementação de log
        ex.printStackTrace()

        val problem: Problem = createProblemBuilder(status, problemType, detail)
        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler(EntidadeNaoEncontradaException::class)
    fun tratarEstadoNaoEncontradoException(ex: EntidadeNaoEncontradaException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.NOT_FOUND
        val problemType = ProblemType.RECURSO_NAO_ENCONTRADO
        val detail = ex.mensagem

        val problem = createProblemBuilder(status, problemType, detail)

        return handleExceptionInternal(ex, problem, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(EntidadeEmUsoException::class)
    fun tratarEntidadeEmUsoExceptionException(ex: EntidadeEmUsoException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.BAD_REQUEST
        val problemType = ProblemType.ENTIDADE_EM_USO
        val detail = ex.mensagem

        val problem = createProblemBuilder(status, problemType, detail)

        return handleExceptionInternal(ex, problem, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(NegocioException::class)
    fun tratarNegocioException(ex: NegocioException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.BAD_REQUEST
        val problemType = ProblemType.ERRO_NEGOCIO
        val detail = ex.mensagem

        val problem = createProblemBuilder(status, problemType, detail)

        return handleExceptionInternal(ex, problem, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    private fun List<Reference>.joinPath(): String {
        return this.joinToString(separator = ".") { ref ->
            ref.fieldName
        }
    }

    private fun handleInvalidFormatException(ex: InvalidFormatException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val path = ex.path.joinPath()

        val problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL
        val detail = "A propriedade $path recebeu o valor ${ex.value} que é de um tipo inválido. " +
                "Corrija e informe um valor compatível com o tipo ${ex.targetType.simpleName}"

        val problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICO_USUARIO_FINAL)

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    private fun handlePropertyBindingException(ex: PropertyBindingException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val path = ex.path.joinPath()

        val problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL
        val detail = "A propriedade $path não existe. Corrija ou remova essa propriedade e tente novamente"

        val problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICO_USUARIO_FINAL)

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    private fun createProblemBuilder(
        status: HttpStatus,
        problemType: ProblemType,
        detail: String,
        userMessage: String? = null,
        problemFields: List<Problem.Field> = listOf()
    ) =
        Problem(
            title = problemType.title,
            type = problemType.uri,
            status = status.value(),
            detail = detail,
            userMessage = userMessage ?: detail,
            fields = problemFields
        )

    private fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val problemType = ProblemType.PARAMETRO_INVALIDO

        val detail = "O parâmetro de URL ${ex.name} recebeu o valor ${ex.value}, " +
                "que é de um tipo inválido. Corrija e informe um valor compatível " +
                "com o tipo ${ex.requiredType?.simpleName}"

        val problem: Problem = createProblemBuilder(status, problemType, detail)

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val rootCause = ex.rootCause
        if (rootCause is InvalidFormatException) {
            return handleInvalidFormatException(rootCause, headers, status, request)
        } else if (rootCause is PropertyBindingException) {
            return handlePropertyBindingException(rootCause, headers, status, request)
        }

        val problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL
        val detail = "O corpo da requisição está inválido. Verifique erro de sintaxe."

        val problem = createProblemBuilder(status, problemType, detail)

        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {

        return if (ex is MethodArgumentTypeMismatchException) {
            handleMethodArgumentTypeMismatch(ex, headers, status, request)
        } else {
            super.handleTypeMismatch(ex, headers, status, request)
        }
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val problemType = ProblemType.RECURSO_NAO_ENCONTRADO
        val detail = "O recurso ${ex.requestURL}, que você tentou acessar, é inexistente."

        val problem: Problem = createProblemBuilder(status, problemType, detail)
        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val problemType = ProblemType.DADOS_INVALIDOS
        val detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."

        val bindingResult = ex.bindingResult
        val problemFields = bindingResult.fieldErrors.map { fieldError ->

            val message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())

            Problem.Field(
                name = fieldError.field,
                userMessage = message
            )
        }

        val problem: Problem = createProblemBuilder(
            status = status,
            problemType = problemType,
            detail = detail,
            problemFields = problemFields
        )

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {

        val problem: Any = if (body == null) {
            Problem(
                title = status.reasonPhrase,
                status = status.value(),
                userMessage = MSG_ERRO_GENERICO_USUARIO_FINAL
            )
        } else {
            if (body is String) {
                Problem(
                    title = body,
                    status = status.value(),
                    userMessage = MSG_ERRO_GENERICO_USUARIO_FINAL
                )
            } else {
                body
            }
        }

        return super.handleExceptionInternal(ex, problem, headers, status, request)
    }
}
