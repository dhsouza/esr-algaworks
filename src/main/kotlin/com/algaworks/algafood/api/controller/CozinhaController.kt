package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.model.Cozinha
import com.algaworks.algafood.domain.repository.CozinhaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cozinhas")
class CozinhaController(
    private val cozinhaRepository: CozinhaRepository
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
}
