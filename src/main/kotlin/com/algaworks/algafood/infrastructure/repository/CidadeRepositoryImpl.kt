package com.algaworks.algafood.infrastructure.repository

import com.algaworks.algafood.domain.model.Cidade
import com.algaworks.algafood.domain.repository.CidadeRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class CidadeRepositoryImpl(
    @PersistenceContext
    private val manager: EntityManager
) : CidadeRepository {
    override fun listar(): List<Cidade> {
        return manager.createQuery("from Cidade", Cidade::class.java).resultList
    }

    override fun buscar(id: Long): Cidade {
        return manager.find(Cidade::class.java, id)
    }

    @Transactional
    override fun salvar(cidade: Cidade): Cidade {
        return manager.merge(cidade)
    }

    @Transactional
    override fun remover(id: Long) {
        val cidade = buscar(id)
        manager.remove(cidade)
    }
}