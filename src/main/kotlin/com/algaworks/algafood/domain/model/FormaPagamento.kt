package com.algaworks.algafood.domain.model

import javax.persistence.*

@Entity
data class FormaPagamento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val descricao: String
)
