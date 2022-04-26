package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.model.Cozinha
import com.algaworks.algafood.domain.repository.CozinhaRepository
import com.algaworks.algafood.domain.service.CadastroCozinhaService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cozinhas")
class CozinhaController(
    private val cozinhaRepository: CozinhaRepository,
    private val cadastroCozinhaService: CadastroCozinhaService,
) {

    @GetMapping
    fun listar(): List<Cozinha> {
        return cozinhaRepository.findAll()
    }

    @GetMapping("/{cozinhaId}")
    fun buscar(@PathVariable cozinhaId: Long): Cozinha {
        return cadastroCozinhaService.buscarOuFalhar(cozinhaId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun adicionar(@RequestBody cozinha: Cozinha): Cozinha {
        return cadastroCozinhaService.salvar(cozinha)
    }

    @PutMapping("/{cozinhaId}")
    fun atualizar(
        @PathVariable cozinhaId: Long,
        @RequestBody cozinha: Cozinha,
    ): Cozinha {
        val cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId)

        return cadastroCozinhaService.salvar(cozinha.copy(id = cozinhaAtual.id))
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remover(@PathVariable cozinhaId: Long) {
        cadastroCozinhaService.excluir(cozinhaId)
    }
}
