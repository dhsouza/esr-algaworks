package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.model.Cozinha
import com.algaworks.algafood.domain.repository.CozinhaRepository
import com.algaworks.algafood.domain.service.CadastroCozinhaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cozinhas")
class CozinhaController(
    private val cozinhaRepository: CozinhaRepository,
    private val cadastroCozinhaService: CadastroCozinhaService
) {

    @GetMapping
    fun listar(): List<Cozinha> {
        return cozinhaRepository.findAll()
    }

    @GetMapping("/{cozinhaId}")
    fun buscar(@PathVariable cozinhaId: Long): ResponseEntity<Cozinha> {
        val cozinha = cozinhaRepository.findById(cozinhaId).orElse(null)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(cozinha)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun adicionar(@RequestBody cozinha: Cozinha): Cozinha {
        return cadastroCozinhaService.salvar(cozinha)
    }

    @PutMapping("/{cozinhaId}")
    fun atualizar(
        @PathVariable cozinhaId: Long,
        @RequestBody cozinha: Cozinha
    ): ResponseEntity<Cozinha> {
        val cozinhaAtual = cozinhaRepository.findById(cozinhaId).orElse(null)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(cadastroCozinhaService.salvar(cozinha.copy(id = cozinhaAtual.id)))
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remover(@PathVariable cozinhaId: Long) {
        cadastroCozinhaService.excluir(cozinhaId)
    }
}
