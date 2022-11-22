package com.algaworks.algafood.core.validation

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

/**
 * Classe serve para definir o messageSource como arquivo padrão de configuração de mensagens
 * isso faz com que possa ser possível utilizar customizações de mensagens no estilo hibernate validator
 * sem ter que criar arquivos seguindo o padrão dos arquivos do hibernate
 */
@Configuration
class ValidationConfig {

    @Bean
    fun validator (messageSource: MessageSource): LocalValidatorFactoryBean {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource)
        return bean
    }
}