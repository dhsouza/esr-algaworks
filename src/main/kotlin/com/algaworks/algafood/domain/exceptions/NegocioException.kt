package com.algaworks.algafood.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class NegocioException : RuntimeException {
    open val mensagem: String

    constructor(mensagem: String) : super(mensagem) {
        this.mensagem = mensagem
    }
    constructor(mensagem: String, causa: Throwable) : super(mensagem, causa) {
        this.mensagem = mensagem
    }
}