package com.algaworks.algafood.infrastructure.repository

import com.algaworks.algafood.domain.model.Estado
import com.algaworks.algafood.domain.repository.EstadoRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class EstadoRepositoryImpl(
    @PersistenceContext
    private val manager: EntityManager
) : EstadoRepository {
    override fun listar(): List<Estado> {
        return manager.createQuery("from Estado", Estado::class.java).resultList
    }

    override fun buscar(id: Long): Estado {
        return manager.find(Estado::class.java, id)
    }

    @Transactional
    override fun salvar(estado: Estado): Estado {
        return manager.merge(estado)
    }

    @Transactional
    override fun remover(id: Long) {
        val estado = buscar(id)
        manager.remove(estado)
    }
}