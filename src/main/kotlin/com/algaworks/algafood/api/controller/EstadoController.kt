package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.model.Estado
import com.algaworks.algafood.domain.repository.EstadoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/estados")
class EstadoController(
    private val estadoRepository: EstadoRepository
) {

    @GetMapping
    fun listar(): List<Estado> {
        return estadoRepository.listar()
    }
}
