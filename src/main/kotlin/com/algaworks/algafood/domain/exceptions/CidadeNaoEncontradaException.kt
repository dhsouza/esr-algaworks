package com.algaworks.algafood.domain.exceptions

class CidadeNaoEncontradaException(override val mensagem: String) : EntidadeNaoEncontradaException(mensagem) {
    constructor(cidadeId: Long) : this("Não existe um cadastro de cidade com código $cidadeId")
}