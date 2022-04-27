package com.algaworks.algafood.api.exceptionhandler

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.exceptions.NegocioException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class ApiExceptioHandler {
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

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun tratarHttpMediaTypeNotSupportedExceptionException(ex: HttpMediaTypeNotSupportedException): ResponseEntity<*> {
        val problema = Problema(dataHora = LocalDateTime.now(), mensagem = "O tipo de mídia não é aceito")

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(problema)
    }
}