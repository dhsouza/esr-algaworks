package com.algaworks.algafood

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

    @LocalServerPort
    private var port: Int = 0

    @Test
    fun `when finding cozinhas should return 200 ok`() {
        given()
            .basePath("/cozinhas")
            .port(port)
            .accept(ContentType.JSON)
		.`when`()
            .get()
		.then()
            .statusCode(HttpStatus.OK.value())
    }
}
