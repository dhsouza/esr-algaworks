package com.algaworks.algafood.api.exceptionhandler

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.exceptions.NegocioException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class ApiExceptioHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(EntidadeNaoEncontradaException::class)
    fun tratarEstadoNaoEncontradoException(ex: EntidadeNaoEncontradaException): ResponseEntity<*> {
        val problema = Problema(dataHora = LocalDateTime.now(), mensagem = ex.mensagem)

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(problema)
    }

    @ExceptionHandler(NegocioException::class)
    fun tratarNegocioException(ex: NegocioException): ResponseEntity<*> {
        val problema = Problema(dataHora = LocalDateTime.now(), mensagem = ex.mensagem)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(problema)
    }

    @ExceptionHandler(EntidadeEmUsoException::class)
    fun tratarEntidadeEmUsoExceptionException(ex: EntidadeEmUsoException): ResponseEntity<*> {
        val problema = Problema(dataHora = LocalDateTime.now(), mensagem = ex.mensagem)

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(problema)
    }
}
