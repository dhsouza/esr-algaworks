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
        return cozinhaRepository.findAll()
    }

    @GetMapping("/{cozinhaId}")
    fun buscar(@PathVariable cozinhaId: Long): ResponseEntity<Cozinha> {
        val cozinha = cozinhaRepository.findById(cozinhaId).orElseGet { null }
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
        val cozinhaAtual = cozinhaRepository.findById(cozinhaId).orElseGet { null }
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(cadastroCozinhaService.salvar(cozinha.copy(id = cozinhaAtual.id)))
    }

    @DeleteMapping("/{cozinhaId}")
    fun remover(@PathVariable cozinhaId: Long): ResponseEntity<*> {
        return try {
            cadastroCozinhaService.excluir(cozinhaId)
            ResponseEntity.noContent().build<Cozinha>()
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.notFound().build<Cozinha>()
        } catch (ex: EntidadeEmUsoException) {
            ResponseEntity.badRequest().body(ex.message)
        }
    }
}
