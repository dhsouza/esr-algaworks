package com.algaworks.algafood.domain.service

import com.algaworks.algafood.domain.exceptions.RestauranteNaoEncontradoException
import com.algaworks.algafood.domain.model.Restaurante
import com.algaworks.algafood.domain.repository.RestauranteRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CadastroRestauranteService(
    private val restauranteRepository: RestauranteRepository,
    private val cadastroCozinhaService: CadastroCozinhaService
) {

    @Transactional
    fun salvar(restaurante: Restaurante): Restaurante {
        val cozinhaId = restaurante.cozinha?.id
        val cozinha = cozinhaId?.let {
            cadastroCozinhaService.buscarOuFalhar(cozinhaId)
        }

        return restauranteRepository.save(restaurante.copy(cozinha = cozinha))
    }

    @Transactional
    fun excluir(restauranteId: Long) {
        try {
            restauranteRepository.deleteById(restauranteId)
        } catch (ex: EmptyResultDataAccessException) {
            throw RestauranteNaoEncontradoException(restauranteId)
        }
    }

    fun buscarOuFalhar(restauranteId: Long): Restaurante {
        return restauranteRepository.findById(restauranteId).orElseThrow {
            RestauranteNaoEncontradoException(restauranteId)
        }
    }
}
