package com.algaworks.algafood.domain.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
class Restaurante(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val nome: String,
    @Column(name = "taxa_frete")
    val taxaFrete: BigDecimal
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Restaurante

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
