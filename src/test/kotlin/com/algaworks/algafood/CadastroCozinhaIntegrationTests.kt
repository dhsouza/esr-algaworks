package com.algaworks.algafood

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
	fun `testar cadastro cozinha com sucesso`() {
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
	fun `deve falhar ao cadastrar cozinha sem nome`() {
		val novaCozinha = Cozinha(
			id = null,
			nome = null,
			restaurantes = null
		)

		assertThrows<ConstraintViolationException> {
			cadastroCozinha.salvar(novaCozinha)
		}
	}
}
