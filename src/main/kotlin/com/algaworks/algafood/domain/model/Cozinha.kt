package com.algaworks.algafood.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Cozinha(
    @field:NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    val nome: String?,

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    val restaurantes: List<Restaurante>?
)
