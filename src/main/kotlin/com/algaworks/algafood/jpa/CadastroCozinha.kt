package com.algaworks.algafood.jpa

import com.algaworks.algafood.domain.model.Cozinha
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
class CadastroCozinha(
    @PersistenceContext
    private val manager: EntityManager
) {

    fun listar(): List<Cozinha> {
        return manager.createQuery("from Cozinha", Cozinha::class.java).resultList
    }

    fun buscar(id: Long): Cozinha {
        return manager.find(Cozinha::class.java, id)
    }

    @Transactional
    fun salvar(cozinha: Cozinha): Cozinha {
        return manager.merge(cozinha)
    }

    @Transactional
    fun remover(id: Long) {
        val cozinha = buscar(id)
        manager.remove(cozinha)
    }
}
