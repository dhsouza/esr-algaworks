package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.model.Restaurante
import com.algaworks.algafood.domain.repository.RestauranteRepository
import com.algaworks.algafood.domain.service.CadastroRestauranteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurantes")
class RestauranteController(
    private val restauranteRepository: RestauranteRepository,
    private val cadastroRestauranteService: CadastroRestauranteService
) {

    @GetMapping
    fun listar(): List<Restaurante> {
        return restauranteRepository.listar()
    }

    @GetMapping("/{restauranteId}")
    fun buscar(@PathVariable restauranteId: Long): ResponseEntity<Restaurante> {
        val restaurante = restauranteRepository.buscar(restauranteId)

        if (restaurante != null) {
            return ResponseEntity.ok(restaurante)
        }

        return ResponseEntity.notFound().build()
    }

    @PostMapping
    fun adicionar(@RequestBody restauranteRequest: Restaurante): ResponseEntity<*> {
        return try {
            val restaurante = cadastroRestauranteService.salvar(restauranteRequest)
            ResponseEntity.status(HttpStatus.CREATED).body(restaurante)
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.badRequest().body(ex.message)
        }
    }

    @PutMapping("/{restauranteId}")
    fun atualizar(
        @PathVariable restauranteId: Long,
        @RequestBody restaurante: Restaurante
    ): ResponseEntity<Restaurante> {
        val restauranteAtual = restauranteRepository.buscar(restauranteId)
        if (restauranteAtual != null) {
            return ResponseEntity.ok(cadastroRestauranteService.salvar(restaurante.copy(id = restauranteAtual.id)))
        }

        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{restauranteId}")
    fun remover(@PathVariable restauranteId: Long): ResponseEntity<Restaurante> {
        return try {
            cadastroRestauranteService.excluir(restauranteId)
            ResponseEntity.noContent().build()
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.notFound().build()
        } catch (ex: EntidadeEmUsoException) {
            ResponseEntity.badRequest().build()
        }
    }
}
