package com.algaworks.algafood.domain.model

import javax.persistence.*

@Entity
data class Cozinha(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val nome: String?
)
