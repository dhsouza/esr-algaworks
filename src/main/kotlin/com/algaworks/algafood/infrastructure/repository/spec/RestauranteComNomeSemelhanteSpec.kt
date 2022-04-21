package com.algaworks.algafood.infrastructure.repository.spec

import com.algaworks.algafood.domain.model.Restaurante
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

data class RestauranteComNomeSemelhanteSpec(val nome: String?) : Specification<Restaurante> {
    override fun toPredicate(
        root: Root<Restaurante>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        return criteriaBuilder.like(root.get("nome"), "%$nome%")
    }
}