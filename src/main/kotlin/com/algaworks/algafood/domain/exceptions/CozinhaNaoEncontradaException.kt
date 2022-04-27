package com.algaworks.algafood.domain.exceptions

data class CozinhaNaoEncontradaException(override val mensagem: String) : EntidadeNaoEncontradaException(mensagem) {
    constructor(cozinhaId: Long) : this("Não existe um cadastro de cozinha com código $cozinhaId")
}