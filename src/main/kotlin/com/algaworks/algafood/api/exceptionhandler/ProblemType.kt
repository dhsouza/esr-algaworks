package com.algaworks.algafood.api.exceptionhandler

enum class ProblemType(path: String, title: String) {

    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");

    var uri: String
    var title: String

    init {
        this.uri = "https://algafood.com.br$path"
        this. title = title
    }
}