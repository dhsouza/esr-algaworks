package com.algaworks.algafood.domain.model

import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Pedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val subtotal: BigDecimal?,
    val taxaFrete: BigDecimal?,
    val valorTotal: BigDecimal?,

    @Embedded
    val enderecoEntrega: Endereco?,
    val status: StatusPedido?,

    @CreationTimestamp
    val dataCriacao: LocalDateTime?,
    val dataConfirmacao: LocalDateTime?,
    val dataCancelamento: LocalDateTime?,
    val dataEntrega: LocalDateTime?,

    @ManyToOne
    @JoinColumn(nullable = false)
    val formaPagamento: FormaPagamento?,

    @ManyToOne
    @JoinColumn(nullable = false)
    val restaurante: Restaurante?,

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    val cliente: Usuario?,

    @OneToMany(mappedBy = "pedido")
    val itens: List<ItemPedido> = ArrayList()
)
