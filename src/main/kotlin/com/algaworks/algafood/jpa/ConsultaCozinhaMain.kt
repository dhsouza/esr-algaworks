package com.algaworks.algafood.jpa

import com.algaworks.algafood.AlgafoodApiApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder

class ConsultaCozinhaMain

fun main(args: Array<String>) {
    val context = SpringApplicationBuilder(AlgafoodApiApplication::class.java)
        .web(WebApplicationType.NONE)
        .run(*args)

    val cadastroCozinha = context.getBean(CadastroCozinha::class.java)

    val cozinhas = cadastroCozinha.listar()
    cozinhas.forEach { println(it.nome) }
}
