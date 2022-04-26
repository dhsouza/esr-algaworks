package com.algaworks.algafood.api.controller

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException
import com.algaworks.algafood.domain.exceptions.NegocioException
import com.algaworks.algafood.domain.model.Cidade
import com.algaworks.algafood.domain.repository.CidadeRepository
import com.algaworks.algafood.domain.service.CadastroCidadeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cidades")
class CidadeController(
    private val cidadeRepository: CidadeRepository,
    private val cadastroCidadeService: CadastroCidadeService,
) {

    @GetMapping
    fun listar(): List<Cidade> {
        return cidadeRepository.findAll()
    }

    @GetMapping("/{cidadeId}")
    fun buscar(@PathVariable cidadeId: Long): Cidade {
        return cadastroCidadeService.buscarOuFalhar(cidadeId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun adicionar(@RequestBody cidadeRequest: Cidade): Cidade {
        try {
            return cadastroCidadeService.salvar(cidadeRequest)
        } catch (ex: EntidadeNaoEncontradaException) {
            throw NegocioException(ex.mensagem)
        }
    }

    @PutMapping("/{cidadeId}")
    fun atualizar(
        @PathVariable cidadeId: Long,
        @RequestBody cidade: Cidade,
    ): Cidade {
        val cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId)

        try {
            return cadastroCidadeService.salvar(cidade.copy(id = cidadeAtual.id))
        } catch (ex: EntidadeNaoEncontradaException) {
            throw NegocioException(ex.mensagem)
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remover(@PathVariable cidadeId: Long) {
        cadastroCidadeService.excluir(cidadeId)
    }
}
