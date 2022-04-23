package com.algaworks.algafood.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
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
