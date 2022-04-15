package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.model.Estado
import com.algaworks.algafood.domain.repository.EstadoRepository
import com.algaworks.algafood.domain.service.CadastroEstadoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/estados")
class EstadoController(
    private val estadoRepository: EstadoRepository,
    private val cadastroEstadoService: CadastroEstadoService
) {

    @GetMapping
    fun listar(): List<Estado> {
        return estadoRepository.listar()
    }

    @GetMapping("/{estadoId}")
    fun buscar(@PathVariable estadoId: Long): ResponseEntity<Estado> {
        val estado = estadoRepository.buscar(estadoId)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(estado)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun adicionar(@RequestBody estado: Estado): Estado {
        return cadastroEstadoService.salvar(estado)
    }

    @PutMapping("/{estadoId}")
    fun atualizar(
        @PathVariable estadoId: Long,
        @RequestBody estado: Estado
    ): ResponseEntity<Estado> {
        val estadoAtual = estadoRepository.buscar(estadoId)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(cadastroEstadoService.salvar(estado.copy(id = estadoAtual.id)))
    }

    @DeleteMapping("/{estadoId}")
    fun remover(@PathVariable estadoId: Long): ResponseEntity<*> {
        return try {
            cadastroEstadoService.excluir(estadoId)
            ResponseEntity.noContent().build<Estado>()
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.notFound().build<Estado>()
        } catch (ex: EntidadeEmUsoException) {
            ResponseEntity.badRequest().body(ex.message)
        }
    }
}
