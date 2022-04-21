package com.algaworks.algafood.infrastructure.repository

import com.algaworks.algafood.domain.model.Restaurante
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Repository
class RestauranteRepositoryImpl(
    @PersistenceContext
    private val manager: EntityManager
) : RestauranteRepositoryQueries {

    override fun find(nome: String?, taxaFreteInicial: BigDecimal?, taxaFreteFinal: BigDecimal?): List<Restaurante> {
        val jpql = StringBuilder()
        jpql.append("from Restaurante where 0 = 0 ")

        val parametros = HashMap<String, Any>()

        if (!nome.isNullOrBlank()) {
            jpql.append("and nome like :nome ")
            parametros["nome"] = "%$nome%"
        }

        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ")
            parametros["taxaInicial"] = taxaFreteInicial
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ")
            parametros["taxaFinal"] = taxaFreteFinal
        }

        val query = manager.createQuery(jpql.toString(), Restaurante::class.java)

        parametros.forEach { (chave, valor) ->
            query.setParameter(chave, valor)
        }

        return query.resultList
    }
}