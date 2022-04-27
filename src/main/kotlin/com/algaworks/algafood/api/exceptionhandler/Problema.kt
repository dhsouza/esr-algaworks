package com.algaworks.algafood.api.exceptionhandler

import java.time.LocalDateTime

data class Problema(
    val dataHora: LocalDateTime,
    val mensagem: String,
)
