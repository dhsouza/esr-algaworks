package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Estado

interface EstadoRepository {

    fun listar(): List<Estado>
    fun buscar(id: Long): Estado?
    fun salvar(estado: Estado): Estado
    fun remover(id: Long)
}