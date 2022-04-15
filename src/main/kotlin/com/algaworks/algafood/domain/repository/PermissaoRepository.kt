package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Permissao

interface PermissaoRepository {

    fun listar(): List<Permissao>
    fun buscar(id: Long): Permissao?
    fun salvar(permissao: Permissao): Permissao
    fun remover(id: Long)
}