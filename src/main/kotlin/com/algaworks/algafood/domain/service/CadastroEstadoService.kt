package com.algaworks.algafood.domain.service

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.model.Estado
import com.algaworks.algafood.domain.repository.EstadoRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class CadastroEstadoService(
    private val estadoRepository: EstadoRepository
) {

    fun salvar(estado: Estado): Estado {
        return estadoRepository.salvar(estado)
    }

    fun excluir(estadoId: Long) {
        try {
            estadoRepository.remover(estadoId)
        } catch (ex: EmptyResultDataAccessException) {
            throw EntidadeNaoEncontradaException("Não existe um cadastro de estado com código $estadoId")
        } catch (ex: DataIntegrityViolationException) {
            throw EntidadeEmUsoException("Estado de código $estadoId não pode ser removida, pois está em uso")
        }
    }
}
