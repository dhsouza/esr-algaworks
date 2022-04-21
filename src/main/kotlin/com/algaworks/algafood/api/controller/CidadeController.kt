package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.model.Cidade
import com.algaworks.algafood.domain.repository.CidadeRepository
import com.algaworks.algafood.domain.service.CadastroCidadeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cidades")
class CidadeController(
    private val cidadeRepository: CidadeRepository,
    private val cadastroCidadeService: CadastroCidadeService
) {

    @GetMapping
    fun listar(): List<Cidade> {
        return cidadeRepository.findAll()
    }

    @GetMapping("/{cidadeId}")
    fun buscar(@PathVariable cidadeId: Long): ResponseEntity<Cidade> {
        val cidade = cidadeRepository.findById(cidadeId).orElse(null)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(cidade)
    }

    @PostMapping
    fun adicionar(@RequestBody cidadeRequest: Cidade): ResponseEntity<*> {
        return try {
            val cidade = cadastroCidadeService.salvar(cidadeRequest)
            ResponseEntity.status(HttpStatus.CREATED).body(cidade)
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.badRequest().body(ex.message)
        }
    }

    @PutMapping("/{cidadeId}")
    fun atualizar(
        @PathVariable cidadeId: Long,
        @RequestBody cidade: Cidade
    ): ResponseEntity<*> {
        val cidadeAtual = cidadeRepository.findById(cidadeId).orElse(null)
            ?: return ResponseEntity.notFound().build<Cidade>()

        return try {
            ResponseEntity.ok(cadastroCidadeService.salvar(cidade.copy(id = cidadeAtual.id)))
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.badRequest().body(ex.message)
        }
    }

    @DeleteMapping("/{cidadeId}")
    fun remover(@PathVariable cidadeId: Long): ResponseEntity<Cidade> {
        return try {
            cadastroCidadeService.excluir(cidadeId)
            ResponseEntity.noContent().build()
        } catch (ex: EntidadeNaoEncontradaException) {
            ResponseEntity.notFound().build()
        }
    }
}
