package com.algaworks.algafood.infrastructure.repository

import com.algaworks.algafood.domain.model.Restaurante
import com.algaworks.algafood.domain.repository.RestauranteRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class RestauranteRepositoryImpl(
    @PersistenceContext
    private val manager: EntityManager
) : RestauranteRepository {
    override fun listar(): List<Restaurante> {
        return manager.createQuery("from Restaurante", Restaurante::class.java).resultList
    }

    override fun buscar(id: Long): Restaurante? {
        return manager.find(Restaurante::class.java, id)
    }

    @Transactional
    override fun salvar(restaurante: Restaurante): Restaurante {
        return manager.merge(restaurante)
    }

    @Transactional
    override fun remover(id: Long) {
        val restaurante = buscar(id) ?: throw EmptyResultDataAccessException(1)
        manager.remove(restaurante)
    }
}