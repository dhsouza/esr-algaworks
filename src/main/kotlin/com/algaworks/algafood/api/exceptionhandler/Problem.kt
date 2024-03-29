package com.algaworks.algafood.api.exceptionhandler

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Problem(
    val title: String,
    val type: String? = null,
    val status: Int,
    val detail: String? = null,
    val userMessage: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val fields: List<Field> = listOf()
) {
    data class Field (val name: String, val userMessage: String)
}
