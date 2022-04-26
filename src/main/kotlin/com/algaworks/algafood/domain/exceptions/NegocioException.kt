package com.algaworks.algafood.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class NegocioException : RuntimeException {
    constructor(mensagem: String) : super(mensagem)
    constructor(mensagem: String, causa: Throwable) : super(mensagem, causa)
}