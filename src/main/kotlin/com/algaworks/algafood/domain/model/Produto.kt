package com.algaworks.algafood.domain.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Produto (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false)
    val descricao: String,

    @Column(nullable = false)
    val preco: BigDecimal,

    @Column(nullable = false)
    val ativo: Boolean,

    @ManyToOne
    @JoinColumn(nullable = false)
    val restaurante: Restaurante
)
