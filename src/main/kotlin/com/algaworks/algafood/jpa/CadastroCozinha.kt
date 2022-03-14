package com.algaworks.algafood.jpa

import com.algaworks.algafood.domain.model.Cozinha
import org.springframework.stereotype.Component
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
}
