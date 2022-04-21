package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Cozinha
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CozinhaRepository : JpaRepository<Cozinha, Long> {

//    fun consultarPorNome(nome: String): List<Cozinha>
}
