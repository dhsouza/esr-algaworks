package com.algaworks.algafood.domain.model

import com.algaworks.algafood.Groups
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.groups.Default

@Entity
data class Cozinha(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @field:NotBlank
    @Column(nullable = false)
    val nome: String?,

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    val restaurantes: List<Restaurante>?
)
