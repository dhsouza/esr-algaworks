package com.algaworks.algafood.infrastructure.repository

import com.algaworks.algafood.domain.repository.CustomJpaRepository
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import java.util.*
import javax.persistence.EntityManager

data class CustomJpaRepositoryImpl<T : Any, ID>(
    private val entityInformation: JpaEntityInformation<T, *>,
    private val manager: EntityManager,
) : SimpleJpaRepository<T, ID>(entityInformation, manager), CustomJpaRepository<T, ID> {

    override fun buscarPrimeiro(): Optional<T> {
        val jpql = "from ${domainClass.name}"

        val entity = manager.createQuery(jpql, domainClass)
            .setMaxResults(1)
            .singleResult

        return Optional.ofNullable(entity)
    }
}