package com.algaworks.algafood.domain.exceptions

data class RestauranteNaoEncontradoException(val restauranteId: Long) :
    EntidadeNaoEncontradaException("Não existe um cadastro de restaurante com código $restauranteId")
