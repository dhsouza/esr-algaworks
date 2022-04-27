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
import java.time.LocalDateTime

@ControllerAdvice
class ApiExceptioHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(EntidadeNaoEncontradaException::class)
    fun tratarEstadoNaoEncontradoException(ex: EntidadeNaoEncontradaException, request: WebRequest): ResponseEntity<*> {
        return handleExceptionInternal(ex, ex.mensagem, HttpHeaders(), HttpStatus.NOT_FOUND, request)
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
        val mensagem = (body ?: status.reasonPhrase) as String
        val problema = Problema(dataHora = LocalDateTime.now(), mensagem = mensagem)

        return super.handleExceptionInternal(ex, problema, headers, status, request)
    }
}
