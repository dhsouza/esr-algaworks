package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Cozinha
import org.springframework.stereotype.Repository

@Repository
interface CozinhaRepository : CustomJpaRepository<Cozinha, Long>
