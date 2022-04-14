package com.algaworks.algafood.infrastructure.repository

import com.algaworks.algafood.domain.model.FormaPagamento
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class FormaPagamentoRepositoryImpl(
    @PersistenceContext
    private val manager: EntityManager
) : FormaPagamentoRepository {
    override fun listar(): List<FormaPagamento> {
        return manager.createQuery("from FormaPagamento", FormaPagamento::class.java).resultList
    }

    override fun buscar(id: Long): FormaPagamento {
        return manager.find(FormaPagamento::class.java, id)
    }

    @Transactional
    override fun salvar(formaPagamento: FormaPagamento): FormaPagamento {
        return manager.merge(formaPagamento)
    }

    @Transactional
    override fun remover(id: Long) {
        val formaPagamento = buscar(id)
        manager.remove(formaPagamento)
    }
}