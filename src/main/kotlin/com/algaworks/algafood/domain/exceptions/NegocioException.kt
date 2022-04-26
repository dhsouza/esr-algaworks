package com.algaworks.algafood.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
data class NegocioException(val mensagem: String) : RuntimeException(mensagem)