package com.algaworks.algafood.api.exceptionhandler

enum class ProblemType(path: String, title: String) {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");

    var uri: String
    var title: String

    init {
        this.uri = "https://algafood.com.br$path"
        this. title = title
    }
}