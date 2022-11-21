package com.algaworks.algafood.domain.exceptions

open class NegocioException : RuntimeException {
    val mensagem: String

    constructor(mensagem: String) : super(mensagem) {
        this.mensagem = mensagem
    }

    constructor(mensagem: String, causa: Throwable) : super(mensagem, causa) {
        this.mensagem = mensagem
    }
}