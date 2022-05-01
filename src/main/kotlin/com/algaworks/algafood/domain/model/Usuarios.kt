package com.algaworks.algafood.domain.model

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long?,

    @Column(nullable = false)
    private val nome: String?,

    @Column(nullable = false)
    private val email: String?,

    @Column(nullable = false)
    private val senha: String?,

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private val dataCadastro: LocalDateTime?,

    @ManyToMany
    @JoinTable(name = "usuario_grupo",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "grupo_id")])
    private val grupos: List<Grupo>
)
