package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Restaurante
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query


interface RestauranteRepository : CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante>{

    @Query("select distinct r from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
    override fun findAll(): List<Restaurante>
}
