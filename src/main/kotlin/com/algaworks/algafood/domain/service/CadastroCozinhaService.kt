package com.algaworks.algafood.domain.service

import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.model.Cozinha
import com.algaworks.algafood.domain.repository.CozinhaRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class CadastroCozinhaService(
    private val cozinhaRepository: CozinhaRepository
) {

    fun salvar(cozinha: Cozinha): Cozinha {
        return cozinhaRepository.save(cozinha)
    }

    fun excluir(cozinhaId: Long) {
        try {
            cozinhaRepository.deleteById(cozinhaId)
        } catch (ex: EmptyResultDataAccessException) {
            throw CozinhaNaoEncontradaException(cozinhaId)
        } catch (ex: DataIntegrityViolationException) {
            throw EntidadeEmUsoException("Cozinha de código $cozinhaId não pode ser removida, pois está em uso")
        }
    }

    fun buscarOuFalhar(cozinhaId: Long): Cozinha {
        return cozinhaRepository.findById(cozinhaId).orElseThrow {
            CozinhaNaoEncontradaException(cozinhaId)
        }
    }
}
