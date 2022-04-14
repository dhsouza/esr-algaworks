package com.algaworks.algafood.infrastructure.repository

import com.algaworks.algafood.domain.model.Permissao
import com.algaworks.algafood.domain.repository.PermissaoRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class PermissaoRepositoryImpl(
    @PersistenceContext
    private val manager: EntityManager
) : PermissaoRepository {
    override fun listar(): List<Permissao> {
        return manager.createQuery("from Permissao", Permissao::class.java).resultList
    }

    override fun buscar(id: Long): Permissao {
        return manager.find(Permissao::class.java, id)
    }

    @Transactional
    override fun salvar(permissao: Permissao): Permissao {
        return manager.merge(permissao)
    }

    @Transactional
    override fun remover(id: Long) {
        val permissao = buscar(id)
        manager.remove(permissao)
    }
}