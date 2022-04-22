package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Restaurante
import java.math.BigDecimal

interface RestauranteRepositoryQueries {

    fun find(nome: String?, taxaFreteInicial: BigDecimal?, taxaFreteFinal: BigDecimal?): List<Restaurante>

    fun findComFreteGratis(nome: String?): List<Restaurante>
}