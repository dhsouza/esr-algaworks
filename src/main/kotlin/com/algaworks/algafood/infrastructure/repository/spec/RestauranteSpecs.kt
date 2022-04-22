package com.algaworks.algafood.infrastructure.repository.spec

import com.algaworks.algafood.domain.model.Restaurante
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal

class RestauranteSpecs {
    companion object Factory {
        @JvmStatic
        fun comNomeSemelhante(nome: String?): Specification<Restaurante> {
            return Specification { root, _, criteriaBuilder -> criteriaBuilder.like(root.get("nome"), "%$nome%")}
        }

        @JvmStatic
        fun comFreteGratis(): Specification<Restaurante> {
            return Specification { root, _, criteriaBuilder ->
                criteriaBuilder.equal(root.get<Restaurante>("taxaFrete"), BigDecimal.ZERO)
            }
        }
    }
}

