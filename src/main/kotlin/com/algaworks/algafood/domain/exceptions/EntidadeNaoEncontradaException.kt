package com.algaworks.algafood.domain.exceptions

data class EntidadeNaoEncontradaException(val mensagem: String) : RuntimeException(mensagem)