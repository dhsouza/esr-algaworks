package com.algaworks.algafood.domain.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class ItemPedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val precoUnitario: BigDecimal?,
    val precoTotal: BigDecimal?,
    val quantidade: Int?,
    val observacao: String?,

    @ManyToOne
    @JoinColumn(nullable = false)
    val pedido: Pedido?,

    @ManyToOne
    @JoinColumn(nullable = false)
    val produto: Produto?
)
