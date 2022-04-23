package com.algaworks.algafood.domain.model

import javax.persistence.*

@Entity
data class Permissao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val nome: String,

    val descricao: String
)
