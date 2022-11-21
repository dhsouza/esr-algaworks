package com.algaworks.algafood.domain.exceptions

data class EstadoNaoEncontradoException(val estadoId: Long) :
    EntidadeNaoEncontradaException("Não existe um cadastro de estado com código $estadoId")
