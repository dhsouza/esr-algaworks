package com.algaworks.algafood.domain.exceptions

data class EstadoNaoEncontradoException(override val mensagem: String) : EntidadeNaoEncontradaException(mensagem) {
    constructor(estadoId: Long) : this("Não existe um cadastro de estado com código $estadoId")
}