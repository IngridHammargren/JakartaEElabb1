package se.iths.jakartaeelabb1.dto;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class BookIT {
    //https://java.testcontainers.org/modules/docker_compose/#adding-this-module-to-your-project-dependencies
    @Container
    public static ComposeContainer environment =
            new ComposeContainer(new File("src/test/resources/compose-test.yml"))
                    .withExposedService("wildfly", 8080, Wait.forHttp("/JakartaEElabb1-1.0-SNAPSHOT/api/books")
                           .forStatusCode(200))
                    .withLocalCompose(true);

    static String host;
    static int port;

    @BeforeAll
    static void beforeAll() {
        host = environment.getServiceHost("wildfly", 8080);
        port = environment.getServicePort("wildfly", 8080);
    }

    @BeforeEach
    void before() {
        RestAssured.baseURI = "http://" + host + "/JakartaEElabb1-1.0-SNAPSHOT/api";
        RestAssured.port = port;
    }


    @Test
    @DisplayName("Test name")
    void testName() {
        Books books = RestAssured.get("/books").then()
                .statusCode(200)
                .extract()
                .as(Books.class);
        assertEquals(List.of(), books.bookDtos());
    }
}
