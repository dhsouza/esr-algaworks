package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.model.Estado
import com.algaworks.algafood.domain.repository.EstadoRepository
import com.algaworks.algafood.domain.service.CadastroEstadoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/estados")
class EstadoController(
    private val estadoRepository: EstadoRepository,
    private val cadastroEstadoService: CadastroEstadoService
) {

    @GetMapping
    fun listar(): List<Estado> {
        return estadoRepository.findAll()
    }

    @GetMapping("/{estadoId}")
    fun buscar(@PathVariable estadoId: Long): Estado {
        return cadastroEstadoService.buscarOuFalhar(estadoId)
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
    ): Estado {
        val estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId)

        return cadastroEstadoService.salvar(estado.copy(id = estadoAtual.id))
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remover(@PathVariable estadoId: Long) {
        cadastroEstadoService.excluir(estadoId)
    }
}
