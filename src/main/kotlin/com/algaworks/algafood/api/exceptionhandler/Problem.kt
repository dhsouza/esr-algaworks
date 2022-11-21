package com.algaworks.algafood.api.exceptionhandler

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Problem(
    val title: String,
    val type: String? = null,
    val status: Int,
    val detail: String? = null,
    val userMessage: String? = null
)
