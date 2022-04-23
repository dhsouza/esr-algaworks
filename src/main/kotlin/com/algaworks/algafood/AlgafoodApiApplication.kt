package com.algaworks.algafood

import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl::class)
class AlgafoodApiApplication

fun main(args: Array<String>) {
	runApplication<AlgafoodApiApplication>(*args)
}
