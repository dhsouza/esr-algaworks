package com.algaworks.algafood.domain.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Restaurante(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val nome: String?,
    @Column(name = "taxa_frete")
    val taxaFrete: BigDecimal?,
    @ManyToOne
    val cozinha: Cozinha?
)
