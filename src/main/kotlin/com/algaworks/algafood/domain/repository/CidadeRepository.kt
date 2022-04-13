package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Cidade

interface CidadeRepository {

    fun listar(): List<Cidade>
    fun buscar(id: Long): Cidade
    fun salvar(cidade: Cidade): Cidade
    fun remover(id: Long)
}