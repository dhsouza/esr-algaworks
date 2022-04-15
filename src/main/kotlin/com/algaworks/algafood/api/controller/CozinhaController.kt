package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
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
        return cozinhaRepository.listar()
    }

    @GetMapping("/{cozinhaId}")
    fun buscar(@PathVariable cozinhaId: Long): ResponseEntity<Cozinha> {
        val cozinha = cozinhaRepository.buscar(cozinhaId)

        if (cozinha != null) {
            return ResponseEntity.ok(cozinha)
        }

        return ResponseEntity.notFound().build()
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
        val cozinhaAtual = cozinhaRepository.buscar(cozinhaId)
        if (cozinhaAtual != null) {
            return ResponseEntity.ok(cadastroCozinhaService.salvar(cozinha.copy(id = cozinhaAtual.id)))
        }

        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{cozinhaId}")
    fun remover(@PathVariable cozinhaId: Long): ResponseEntity<Cozinha> {
        return try {
            cadastroCozinhaService.excluir(cozinhaId)
            ResponseEntity.noContent().build()
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.notFound().build()
        } catch (ex: EntidadeEmUsoException) {
            ResponseEntity.badRequest().build()
        }
    }
}
