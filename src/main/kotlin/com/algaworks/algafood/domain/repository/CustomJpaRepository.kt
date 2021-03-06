package com.algaworks.algafood.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface CustomJpaRepository<T, ID> : JpaRepository<T, ID> {

    fun buscarPrimeiro(): Optional<T>
}