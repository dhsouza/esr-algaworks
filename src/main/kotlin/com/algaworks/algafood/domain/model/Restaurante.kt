package com.algaworks.algafood.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@Entity
data class Restaurante(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @field:NotBlank
    @Column(nullable = false)
    val nome: String?,

    @field:NotNull
    @field:PositiveOrZero
    @Column(name = "taxa_frete")
    val taxaFrete: BigDecimal?,

    @field:NotNull
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    val cozinha: Cozinha?,

    @JsonIgnore
    @Embedded
    val endereco: Endereco?,

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    val dataCadastro: LocalDateTime?,

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    val dataAtualizacao: LocalDateTime?,

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
        joinColumns = [JoinColumn(name = "restaurante_id")],
        inverseJoinColumns = [JoinColumn(name = "forma_pagamento_id")])
    val formasPagamento: List<FormaPagamento>?,

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    val produtos: List<Produto>?
)
