package com.algaworks.algafood.domain.exceptions

data class RestauranteNaoEncontradoException(override val mensagem: String) : EntidadeNaoEncontradaException(mensagem) {
    constructor(restauranteId: Long) : this("Não existe um cadastro de restaurante com código $restauranteId")
}