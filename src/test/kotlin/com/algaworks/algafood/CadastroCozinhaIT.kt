package com.algaworks.algafood

import com.algaworks.algafood.domain.model.Cozinha
import io.restassured.RestAssured
import io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.flywaydb.core.Flyway
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var flyway: Flyway

    @BeforeEach
    fun setup() {
        enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.port = port
        RestAssured.basePath = "/cozinhas"

        flyway.migrate()
    }

    @Test
    fun `when finding cozinhas should return 200 ok`() {
        given()
            .accept(ContentType.JSON)
		.`when`()
            .get()
		.then()
            .statusCode(HttpStatus.OK.value())
    }

    @Test
    fun `when finding cozinhas should return four cozinhas`() {
        given()
            .accept(ContentType.JSON)
        .`when`()
            .get()
        .then()
            .body("", hasSize<Cozinha>(4))
    }

    @Test
    fun `when saving a cozinha should return 201 created`() {
        given()
            .body("{ \"nome\": \"Chinesa\" }")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .`when`()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value())
    }
}
