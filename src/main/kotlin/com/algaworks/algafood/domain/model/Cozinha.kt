package com.algaworks.algafood.domain.model

import com.algaworks.algafood.Groups
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Cozinha(
    @field:NotNull(groups = [Groups.CadastroRestaurante::class])
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @field:NotBlank
    val nome: String?,

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    val restaurantes: List<Restaurante>?
)
