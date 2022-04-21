package com.algaworks.algafood.domain.repository

import com.algaworks.algafood.domain.model.Estado
import org.springframework.data.jpa.repository.JpaRepository

interface EstadoRepository : JpaRepository<Estado, Long>
