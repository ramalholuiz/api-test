package com.example.json1;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Json1StepDefinitions {
    private String endpoint;
    private Response response;

    @Before
    public void setup() {
        RestAssured.baseURI = "https://api-desafio-qa.onrender.com";
    }

    @Given("the API endpoint is {string}")
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("I send a GET request")
    public void sendGETRequest() {
        response = given().when().get(endpoint);
    }

    @Then("the status code is {int}")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response content type is JSON")
    public void verifyContentTypeJSON() {
        response.then().contentType("application/json");
    }

    @Then("the response JSON is valid")
    public void verifyResponseJSON() {
        response.then()

                .body("produtos.id", everyItem(notNullValue()))
                .body("produtos.nome", everyItem(not(isEmptyOrNullString())))
                .body("produtos.preco", everyItem(greaterThan(0.0)))
                .body("produtos.disponivel", everyItem(instanceOf(Boolean.class)))

                // Validate User
                .body("usuario.nome", not(isEmptyOrNullString()))
                .body("usuario.idade", greaterThan(0))
                .body("usuario.email", matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$"))

                // Validate Configurations
                .body("configuracoes.notificacoes", instanceOf(Boolean.class))
                .body("configuracoes.tema", not(isEmptyOrNullString()))
                .body("configuracoes.idioma", not(isEmptyOrNullString()))

                // Validate Address
                .body("endereco.rua", not(isEmptyOrNullString()))
                .body("endereco.numero", greaterThan(0))
                .body("endereco.cidade", not(isEmptyOrNullString()))
                .body("endereco.estado", matchesPattern("^[A-Z]{2}$"))
                .body("endereco.cep", matchesPattern("^[0-9]{5}-[0-9]{3}$"))

                // Validate Order History
                .body("historicoDePedidos.pedidoId", everyItem(notNullValue()))
                .body("historicoDePedidos.produto", everyItem(not(isEmptyOrNullString())))
                .body("historicoDePedidos.quantidade", everyItem(greaterThan(0)))
                .body("historicoDePedidos.precoTotal", everyItem(greaterThan(0.0)))

                // Validate Current Cart
                .body("carrinhoAtual.produtos.produtoId", everyItem(notNullValue()))
                .body("carrinhoAtual.produtos.quantidade", everyItem(greaterThan(0)))
                .body("carrinhoAtual.precoTotal", greaterThan(0.0))

                // Validate Payment Method
                .body("metodoDePagamento.cartao.numero", matchesPattern("^[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}$"))
                .body("metodoDePagamento.cartao.validade", matchesPattern("^(0[1-9]|1[0-2])/20[2-9]{2}$"))
                .body("metodoDePagamento.cartao.cvv", matchesPattern("^[0-9]{3,4}$"))

                // Validate Contact
                .body("contato.telefone", matchesPattern("^\\([0-9]{2}\\) [0-9]{5}-[0-9]{4}$"))
                .body("contato.emailSecundario", matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$"))

                // Validate Last Purchase
                .body("ultimaCompra.data", matchesPattern("^\\d{4}-\\d{2}-\\d{2}$"))
                .body("ultimaCompra.valor", greaterThan(0.0))
                .body("ultimaCompra.produto", not(isEmptyOrNullString()))

                // Validate Recommendations
                .body("recomendacoes.preco", everyItem(greaterThan(0.0)))

                // Validate Usage Statistics
                .body("estatisticasDeUso.horasConectado", greaterThan(0))
                .body("estatisticasDeUso.diasAtivo", greaterThan(0))

                // Validate Friends
                .body("amigos.nome", everyItem(not(isEmptyOrNullString())))
                .body("amigos.contato", everyItem(matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$")))

                // Validate Preferences
                .body("preferencias.categoriasFavoritas", everyItem(not(isEmptyOrNullString())))
                .body("preferencias.notificarPromocoes", instanceOf(Boolean.class));
    }
}
