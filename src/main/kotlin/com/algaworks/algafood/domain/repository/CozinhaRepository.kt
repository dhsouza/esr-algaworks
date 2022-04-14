package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Cozinha

interface CozinhaRepository {

    fun listar(): List<Cozinha>
    fun buscar(id: Long): Cozinha?
    fun salvar(cozinha: Cozinha): Cozinha
    fun remover(id: Long)
}