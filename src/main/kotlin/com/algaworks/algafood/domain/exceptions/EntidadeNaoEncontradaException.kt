package com.algaworks.algafood.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

//@ResponseStatus(HttpStatus.NOT_FOUND)
class EntidadeNaoEncontradaException(status: HttpStatus, mensagem: String) : ResponseStatusException(status, mensagem) {
    constructor(mensagem: String) : this(HttpStatus.NOT_FOUND, mensagem)
}