package com.algaworks.algafood.domain.exceptions

data class CozinhaNaoEncontradaException(val cozinhaId: Long) :
    EntidadeNaoEncontradaException("Não existe um cadastro de cozinha com código $cozinhaId")
