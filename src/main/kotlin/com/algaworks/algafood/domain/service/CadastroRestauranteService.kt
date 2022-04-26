package com.algaworks.algafood.domain.service

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.model.Restaurante
import com.algaworks.algafood.domain.repository.CozinhaRepository
import com.algaworks.algafood.domain.repository.RestauranteRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class CadastroRestauranteService(
    private val restauranteRepository: RestauranteRepository,
    private val cozinhaRepository: CozinhaRepository
) {

    fun salvar(restaurante: Restaurante): Restaurante {
        val cozinhaId = restaurante.cozinha?.id
        val cozinha = cozinhaId?.let {
            cozinhaRepository.findById(it).orElseThrow {
                EntidadeNaoEncontradaException("Não existe cadastro de cozinha com código $cozinhaId")
            }
        }

        return restauranteRepository.save(restaurante.copy(cozinha = cozinha))
    }

    fun excluir(restauranteId: Long) {
        try {
            restauranteRepository.deleteById(restauranteId)
        } catch (ex: EmptyResultDataAccessException) {
            throw EntidadeNaoEncontradaException("Não existe um cadastro de restaurante com código $restauranteId")
        }
    }

    fun buscarOuFalhar(restauranteId: Long): Restaurante {
        return restauranteRepository.findById(restauranteId).orElseThrow {
            EntidadeNaoEncontradaException("Não existe um cadastro de restaurante com código $restauranteId")
        }
    }
}
