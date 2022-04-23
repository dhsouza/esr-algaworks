package com.algaworks.algafood.domain.model

import javax.persistence.*

@Entity
class Grupo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long?,

    @Column(nullable = false)
    private val nome: String?,

    @ManyToMany
    @JoinTable(name = "grupo_permissao",
        joinColumns = [JoinColumn(name = "grupo_id")],
        inverseJoinColumns = [JoinColumn(name = "permissao_id")])
    private val permissoes: List<Permissao>
)
