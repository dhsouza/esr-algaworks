package com.algaworks.algafood

import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException
import com.algaworks.algafood.domain.model.Cozinha
import com.algaworks.algafood.domain.service.CadastroCozinhaService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.validation.ConstraintViolationException

@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private lateinit var cadastroCozinha: CadastroCozinhaService

	@Test
	fun `when saving cozinha with correct data should assign an id`() {
		//cenário
		var novaCozinha = Cozinha(
			id = null,
			nome = "Chinesa",
			restaurantes = null
		)

		//ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha)

		//valicação
		assertThat(novaCozinha).isNotNull
		assertThat(novaCozinha.id).isNotNull
	}

	@Test
	fun `should fail when saving a cozinha without name`() {
		val novaCozinha = Cozinha(
			id = null,
			nome = null,
			restaurantes = null
		)

		assertThrows<ConstraintViolationException> {
			cadastroCozinha.salvar(novaCozinha)
		}
	}

	@Test
	fun `should fail when deleting a cozinha that's already in use`() {
		assertThrows<EntidadeEmUsoException> {
			cadastroCozinha.excluir(1L)
		}
	}

	@Test
	fun `should fail when deleting a non existent cozinha`() {
		assertThrows<CozinhaNaoEncontradaException> {
			cadastroCozinha.excluir(100L)
		}
	}
}
