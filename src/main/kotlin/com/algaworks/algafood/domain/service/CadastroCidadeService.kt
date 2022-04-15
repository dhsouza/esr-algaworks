package com.algaworks.algafood.domain.service

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.model.Cidade
import com.algaworks.algafood.domain.repository.CidadeRepository
import com.algaworks.algafood.domain.repository.EstadoRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class CadastroCidadeService(
    private val cidadeRepository: CidadeRepository,
    private val estadoRepository: EstadoRepository
) {

    fun salvar(cidade: Cidade): Cidade {
        val estadoId = cidade.estado.id
        val estado = estadoRepository.buscar(estadoId)
            ?: throw EntidadeNaoEncontradaException("Não existe cadastro de estado com código $estadoId")

        return cidadeRepository.salvar(cidade.copy(estado = estado))
    }

    fun excluir(cidadeId: Long) {
        try {
            cidadeRepository.remover(cidadeId)
        } catch (ex: EmptyResultDataAccessException) {
            throw EntidadeNaoEncontradaException("Não existe um cadastro de estado com código $cidadeId")
        }
    }
}
