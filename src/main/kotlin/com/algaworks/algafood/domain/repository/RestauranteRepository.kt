package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Restaurante
import org.springframework.data.jpa.repository.JpaRepository

interface RestauranteRepository : JpaRepository<Restaurante, Long>
