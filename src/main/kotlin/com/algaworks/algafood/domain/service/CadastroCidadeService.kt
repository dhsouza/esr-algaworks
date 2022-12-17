package com.algaworks.algafood.domain.service

import com.algaworks.algafood.domain.exceptions.CidadeNaoEncontradaException
import com.algaworks.algafood.domain.model.Cidade
import com.algaworks.algafood.domain.repository.CidadeRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CadastroCidadeService(
    private val cidadeRepository: CidadeRepository,
    private val cadastroEstadoService: CadastroEstadoService
) {

    @Transactional
    fun salvar(cidade: Cidade): Cidade {
        val estadoId = cidade.estado.id
        val estado = cadastroEstadoService.buscarOuFalhar(estadoId)

        return cidadeRepository.save(cidade.copy(estado = estado))
    }

    @Transactional
    fun excluir(cidadeId: Long) {
        try {
            cidadeRepository.deleteById(cidadeId)
        } catch (ex: EmptyResultDataAccessException) {
            throw CidadeNaoEncontradaException(cidadeId)
        }
    }

    fun buscarOuFalhar(cidadeId: Long): Cidade {
        return cidadeRepository.findById(cidadeId).orElseThrow {
            CidadeNaoEncontradaException(cidadeId)
        }
    }
}
