package com.algaworks.algafood.domain.service

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
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
        return cozinhaRepository.salvar(cozinha)
    }

    fun excluir(cozinhaId: Long) {
        try {
            cozinhaRepository.remover(cozinhaId)
        } catch (ex: EmptyResultDataAccessException) {
            throw EntidadeNaoEncontradaException("Não existe um cadastro de cozinha com código $cozinhaId")
        } catch (ex: DataIntegrityViolationException) {
            throw EntidadeEmUsoException("Cozinha de código $cozinhaId não pode ser removida, pois está em uso")
        }
    }
}
