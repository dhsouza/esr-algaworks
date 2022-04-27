package com.algaworks.algafood.domain.exceptions

abstract class EntidadeNaoEncontradaException(override val mensagem: String) : NegocioException(mensagem)

