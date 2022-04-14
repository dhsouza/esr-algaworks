package com.algaworks.algafood.domain.model

import javax.persistence.*

@Entity
data class Estado(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val nome: String
)
