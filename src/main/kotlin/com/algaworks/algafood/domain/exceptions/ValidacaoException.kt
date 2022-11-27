package com.algaworks.algafood.domain.exceptions

import org.springframework.validation.BindingResult

data class ValidacaoException(val bindingResult: BindingResult) : RuntimeException()
