package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.model.Restaurante
import com.algaworks.algafood.domain.repository.RestauranteRepository
import com.algaworks.algafood.domain.service.CadastroRestauranteService
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.Factory.comFreteGratis
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.Factory.comNomeSemelhante
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ReflectionUtils
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/restaurantes")
class RestauranteController(
    private val restauranteRepository: RestauranteRepository,
    private val cadastroRestauranteService: CadastroRestauranteService
) {

    @GetMapping
    fun listar(): List<Restaurante> {
        return restauranteRepository.findAll()
    }

    @GetMapping("/{restauranteId}")
    fun buscar(@PathVariable restauranteId: Long): ResponseEntity<Restaurante> {
        val restaurante = restauranteRepository.findById(restauranteId).orElse(null)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(restaurante)
    }

    @GetMapping("/teste")
    fun teste(nome: String?, taxaFreteInicial: BigDecimal?, taxaFreteFinal: BigDecimal?): List<Restaurante> {
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal)
    }

    @GetMapping("/com-frete-gratis")
    fun restaurantesComFreteGratis(nome: String?, taxaFreteInicial: BigDecimal?, taxaFreteFinal: BigDecimal?): List<Restaurante> {
        return restauranteRepository.findAll(
            comNomeSemelhante(nome).and(comFreteGratis())
        )
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
    ): ResponseEntity<*> {
        val restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null)
            ?: return ResponseEntity.notFound().build<Restaurante>()

        return try {
            ResponseEntity.ok(cadastroRestauranteService.salvar(restaurante.copy(id = restauranteAtual.id)))
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.badRequest().body(ex.message)
        }
    }

    @DeleteMapping("/{restauranteId}")
    fun remover(@PathVariable restauranteId: Long): ResponseEntity<Restaurante> {
        return try {
            cadastroRestauranteService.excluir(restauranteId)
            ResponseEntity.noContent().build()
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.notFound().build()
        }
    }

    @PatchMapping("/{restauranteId}")
    fun atualizarParcial(
        @PathVariable restauranteId: Long,
        @RequestBody campos: Map<String, Any>
    ): ResponseEntity<*> {
        val restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null)
            ?: return ResponseEntity.notFound().build<Restaurante>()

        merge(campos, restauranteAtual)

        return atualizar(restauranteId, restauranteAtual)
    }

    private fun merge(dadosOrigem: Map<String, Any>, restauranteDestino: Restaurante) {
        val restauranteOrigem = jacksonObjectMapper().convertValue(dadosOrigem, Restaurante::class.java)

        dadosOrigem.forEach { (nomePropriedade) ->
            val field = ReflectionUtils.findField(Restaurante::class.java, nomePropriedade)
                ?: return@forEach
            field.isAccessible = true

            val novoValor = ReflectionUtils.getField(field, restauranteOrigem)
            ReflectionUtils.setField(field, restauranteDestino, novoValor)
        }
    }
}
