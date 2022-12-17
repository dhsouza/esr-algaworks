package com.algaworks.algafood.domain.service

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EstadoNaoEncontradoException
import com.algaworks.algafood.domain.model.Estado
import com.algaworks.algafood.domain.repository.EstadoRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CadastroEstadoService(
    private val estadoRepository: EstadoRepository
) {

    @Transactional
    fun salvar(estado: Estado): Estado {
        return estadoRepository.save(estado)
    }

    @Transactional
    fun excluir(estadoId: Long) {
        try {
            estadoRepository.deleteById(estadoId)
        } catch (ex: EmptyResultDataAccessException) {
            throw EstadoNaoEncontradoException(estadoId)
        } catch (ex: DataIntegrityViolationException) {
            throw EntidadeEmUsoException("Estado de código $estadoId não pode ser removida, pois está em uso")
        }
    }

    fun buscarOuFalhar(estadoId: Long): Estado {
        return estadoRepository.findById(estadoId).orElseThrow {
            EstadoNaoEncontradoException(estadoId)
        }
    }
}
