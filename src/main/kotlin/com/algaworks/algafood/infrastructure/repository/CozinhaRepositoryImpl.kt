package com.algaworks.algafood.infrastructure.repository

import com.algaworks.algafood.domain.model.Cozinha
import com.algaworks.algafood.domain.repository.CozinhaRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class CozinhaRepositoryImpl(
    @PersistenceContext
    private val manager: EntityManager
) : CozinhaRepository {
    override fun listar(): List<Cozinha> {
        return manager.createQuery("from Cozinha", Cozinha::class.java).resultList
    }

    override fun buscar(id: Long?): Cozinha? {
        return manager.find(Cozinha::class.java, id)
    }

    @Transactional
    override fun salvar(cozinha: Cozinha): Cozinha {
        return manager.merge(cozinha)
    }

    @Transactional
    override fun remover(id: Long) {
        val cozinha = buscar(id) ?: throw EmptyResultDataAccessException(1)
        manager.remove(cozinha)
    }
}