package com.algaworks.algafood.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class EntidadeNaoEncontradaException(mensagem: String) : RuntimeException(mensagem)