package com.algaworks.algafood.api.exceptionhandler

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.exceptions.NegocioException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ApiExceptioHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(EntidadeNaoEncontradaException::class)
    fun tratarEstadoNaoEncontradoException(ex: EntidadeNaoEncontradaException, request: WebRequest): ResponseEntity<*> {
        val status = HttpStatus.NOT_FOUND
        val problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA
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

    private fun handleInvalidFormatException(ex: InvalidFormatException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val path = ex.path.joinToString(separator = ".") { ref ->
            ref.fieldName
        }

        val problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL
        val detail = "A propriedade $path recebeu o valor ${ex.value} que é de um tipo inválido. " +
                "Corrija e informe um valor compatível com o tipo ${ex.targetType.simpleName}"

        val problem = createProblemBuilder(status, problemType, detail)

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
        }

        val problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL
        val detail = "O corpo da requisição está inválido. Verifique erro de sintaxe."

        val problem = createProblemBuilder(status, problemType, detail)

        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
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
                status = status.value()
            )
        } else {
            if (body is String) {
                Problem(
                    title = body,
                    status = status.value()
                )
            } else {
                body
            }
        }

        return super.handleExceptionInternal(ex, problem, headers, status, request)
    }

    fun createProblemBuilder(status: HttpStatus, problemType: ProblemType, detail: String) =
        Problem(
            title = problemType.title,
            type = problemType.uri,
            status = status.value(),
            detail = detail
        )
}
