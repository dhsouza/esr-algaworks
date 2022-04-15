package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.FormaPagamento

interface FormaPagamentoRepository {

    fun listar(): List<FormaPagamento>
    fun buscar(id: Long): FormaPagamento?
    fun salvar(formaPagamento: FormaPagamento): FormaPagamento
    fun remover(id: Long)
}