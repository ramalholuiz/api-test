package com.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    private static String token;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://api-desafio-qa.onrender.com";
    }

    @Test
    public void testSuccessfulLogin() {
        String requestBody = "{\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"password\"\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/login-hard")
                .then()
                .log().all()
                .statusCode(201)
                .body("data[0].form[0].cam[0].in[0].gen[0].form_token[0].in_token[0].gen_token[0].refresh[0].bearer[0].refresh_token[0].show.token", notNullValue())
                .body("data[0].form[0].cam[0].in[0].gen[0].form_token[0].in_token[0].gen_token[0].refresh[0].bearer[0].refresh_token[0].show.message", equalTo("Achou que seria fácil né chegar no token ..hahaha"))
                .extract()
                .response();

        token = response.jsonPath().getString("data[0].form[0].cam[0].in[0].gen[0].form_token[0].in_token[0].gen_token[0].refresh[0].bearer[0].refresh_token[0].show.token");
    }

    @Test
    public void testInvalidUsernameOrPassword() {
        String requestBody = "{\n" +
                "    \"username\": \"invalid\",\n" +
                "    \"password\": \"invalid\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/login-hard")
                .then()
                .log().all()
                .statusCode(401)
                .body("error", equalTo("Credenciais inválidas"));
//                .body("error", equalTo("Invalid credentials provided."));
    }

    @Test
    public void testUnauthorizedAccess() {
        String requestBody = "{\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"wrongpassword\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/login-hard")
                .then()
                .log().all()
                .statusCode(401)
                .body("error", equalTo("Credenciais inválidas"));
//                .body("error", equalTo("Authentication failed."));
    }
}
