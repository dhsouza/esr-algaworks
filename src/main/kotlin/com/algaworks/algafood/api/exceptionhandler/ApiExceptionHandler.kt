package com.algaworks.algafood.api.exceptionhandler

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.exceptions.NegocioException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

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

    @ExceptionHandler(NegocioException::class)
    fun tratarNegocioException(ex: NegocioException, request: WebRequest): ResponseEntity<*> {
        return handleExceptionInternal(ex, ex.mensagem, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(EntidadeEmUsoException::class)
    fun tratarEntidadeEmUsoExceptionException(ex: EntidadeEmUsoException, request: WebRequest): ResponseEntity<*> {
        return handleExceptionInternal(ex, ex.mensagem, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
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
