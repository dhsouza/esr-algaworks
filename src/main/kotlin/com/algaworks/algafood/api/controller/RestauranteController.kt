package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.exceptions.NegocioException
import com.algaworks.algafood.domain.model.Restaurante
import com.algaworks.algafood.domain.repository.RestauranteRepository
import com.algaworks.algafood.domain.service.CadastroRestauranteService
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.NestedExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.util.ReflectionUtils
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/restaurantes")
class RestauranteController(
    private val restauranteRepository: RestauranteRepository,
    private val cadastroRestauranteService: CadastroRestauranteService,
) {

    @GetMapping
    fun listar(): List<Restaurante> {
        return restauranteRepository.findAll()
    }

    @GetMapping("/{restauranteId}")
    fun buscar(@PathVariable restauranteId: Long): Restaurante {
        return cadastroRestauranteService.buscarOuFalhar(restauranteId)
    }

    @GetMapping("/teste")
    fun teste(nome: String?, taxaFreteInicial: BigDecimal?, taxaFreteFinal: BigDecimal?): List<Restaurante> {
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal)
    }

    @GetMapping("/com-frete-gratis")
    fun restaurantesComFreteGratis(nome: String?): List<Restaurante> {
        return restauranteRepository.findComFreteGratis(nome)
    }

    @GetMapping("/primeiro")
    fun restaurantePrimeiro(): Optional<Restaurante> {
        return restauranteRepository.buscarPrimeiro()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun adicionar(@RequestBody @Valid restauranteRequest: Restaurante): Restaurante {
        try {
            return cadastroRestauranteService.salvar(restauranteRequest)
        } catch (ex: EntidadeNaoEncontradaException) {
            throw NegocioException(ex.mensagem, ex)
        }
    }

    @PutMapping("/{restauranteId}")
    fun atualizar(
        @PathVariable restauranteId: Long,
        @RequestBody restaurante: Restaurante,
    ): Restaurante {
        val restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId)

        try {
            return cadastroRestauranteService.salvar(
                restaurante.copy(
                    id = restauranteAtual.id,
                    formasPagamento = restauranteAtual.formasPagamento,
                    endereco = restauranteAtual.endereco,
                    dataCadastro = restauranteAtual.dataCadastro,
                    produtos = restauranteAtual.produtos
                )
            )
        } catch (ex: EntidadeNaoEncontradaException) {
            throw NegocioException(ex.mensagem, ex)
        }
    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remover(@PathVariable restauranteId: Long) {
        cadastroRestauranteService.excluir(restauranteId)
    }

    @PatchMapping("/{restauranteId}")
    fun atualizarParcial(
        @PathVariable restauranteId: Long,
        @RequestBody campos: Map<String, Any>,
        request: HttpServletRequest
    ): Restaurante {
        val restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId)

        merge(campos, restauranteAtual, request)

        return atualizar(restauranteId, restauranteAtual)
    }

    private fun merge(dadosOrigem: Map<String, Any>, restauranteDestino: Restaurante, request: HttpServletRequest) {
        val servletRequest = ServletServerHttpRequest(request)

        try {
            val restauranteOrigem = jacksonObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true)
                .convertValue(dadosOrigem, Restaurante::class.java)

            dadosOrigem.forEach { (nomePropriedade) ->
                val field = ReflectionUtils.findField(Restaurante::class.java, nomePropriedade)
                    ?: return@forEach
                field.isAccessible = true

                val novoValor = ReflectionUtils.getField(field, restauranteOrigem)
                ReflectionUtils.setField(field, restauranteDestino, novoValor)
            }
        } catch (ex: IllegalArgumentException) {
            val rootCause = NestedExceptionUtils.getRootCause(ex)
            throw HttpMessageNotReadableException(ex.message!!, rootCause, servletRequest)
        }
    }
}
