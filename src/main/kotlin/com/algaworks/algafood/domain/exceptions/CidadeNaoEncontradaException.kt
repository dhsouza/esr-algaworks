package com.algaworks.algafood.domain.exceptions

data class CidadeNaoEncontradaException(val cidadeId: Long) :
    EntidadeNaoEncontradaException("Não existe um cadastro de cidade com código $cidadeId")
