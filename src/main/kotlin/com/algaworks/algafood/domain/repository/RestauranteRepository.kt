package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Restaurante
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface RestauranteRepository : JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante>
