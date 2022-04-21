package com.algaworks.algafood.infrastructure.repository

import com.algaworks.algafood.domain.model.Restaurante
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Predicate


@Repository
class RestauranteRepositoryImpl(
    @PersistenceContext
    private val manager: EntityManager
) : RestauranteRepositoryQueries {

    override fun find(nome: String?, taxaFreteInicial: BigDecimal?, taxaFreteFinal: BigDecimal?): List<Restaurante> {
        val builder = manager.criteriaBuilder
        val criteria = builder.createQuery(Restaurante::class.java)
        val root = criteria.from(Restaurante::class.java)

        val predicates = mutableListOf<Predicate>()

        if (!nome.isNullOrEmpty()) {
            predicates.add(builder.like(root.get("nome"), "%$nome%"))
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial))
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal))
        }

        criteria.where(*predicates.toTypedArray())

        val query = manager.createQuery(criteria)
        return query.resultList
    }
}
