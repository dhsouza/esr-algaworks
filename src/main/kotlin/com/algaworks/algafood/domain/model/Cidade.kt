package com.algaworks.algafood.domain.model

import javax.persistence.*

@Entity
data class Cidade(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val nome: String,

    @ManyToOne
    val estado: Estado
)
