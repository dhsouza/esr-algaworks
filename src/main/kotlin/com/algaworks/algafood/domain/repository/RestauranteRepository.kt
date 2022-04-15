package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Restaurante

interface RestauranteRepository {

    fun listar(): List<Restaurante>
    fun buscar(id: Long): Restaurante?
    fun salvar(restaurante: Restaurante): Restaurante
    fun remover(id: Long)
}