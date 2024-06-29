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
        response = given()
                .log().all()  // Log the request
                .when().get(endpoint)
                .then()
                .log().all()  // Log the response
                .extract().response();
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
                // Validate "amigos"
                .body("amigos.contato", everyItem(matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$")))
                .body("amigos.nome", everyItem(not(equalTo(""))))

                // Validate "assinaturaNewsletter"
                .body("assinaturaNewsletter", instanceOf(Boolean.class))

                // Validate "carrinhoAtual"
                .body("carrinhoAtual.precoTotal", anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class)))
                .body("carrinhoAtual.produtos.produtoId", everyItem(anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class))))
                .body("carrinhoAtual.produtos.quantidade", everyItem(anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class))))

                // Validate "configuracoes"
                .body("configuracoes.idioma", equalTo("pt-BR"))
                .body("configuracoes.notificacoes", instanceOf(Boolean.class))
                .body("configuracoes.tema", not(equalTo("")))

                // Validate "contato"
                .body("contato.emailSecundario", matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$"))
                .body("contato.telefone", matchesPattern("^\\([0-9]{2}\\) [0-9]{5}-[0-9]{4}$"))

                // Validate "endereco"
                .body("endereco.cep", matchesPattern("^[0-9]{5}-[0-9]{3}$"))
                .body("endereco.cidade", not(equalTo("")))
                .body("endereco.estado", matchesPattern("^[A-Z]{2}$"))
                .body("endereco.numero", anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class)))
                .body("endereco.rua", not(equalTo("")))

                // Validate "estatisticasDeUso"
                .body("estatisticasDeUso.diasAtivo", anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class)))
                .body("estatisticasDeUso.horasConectado", anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class)))

                // Validate "historicoDePedidos"
                .body("historicoDePedidos.pedidoId", everyItem(anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class))))
                .body("historicoDePedidos.produto", everyItem(not(equalTo(""))))
                .body("historicoDePedidos.quantidade", everyItem(anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class))))
                .body("historicoDePedidos.precoTotal", everyItem(anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class))))

                // Validate "metodoDePagamento"
                .body("metodoDePagamento.cartao.cvv", matchesPattern("^[0-9]{3,4}$"))
                .body("metodoDePagamento.cartao.numero", matchesPattern("^[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}$"))
                .body("metodoDePagamento.cartao.validade", matchesPattern("^(0[1-9]|1[0-2])/20[2-9]{2}$"))

                // Validate "perfilPublico"
                .body("perfilPublico", instanceOf(Boolean.class))

                // Validate "preferencias"
                .body("preferencias.categoriasFavoritas", everyItem(not(equalTo(""))))
                .body("preferencias.notificarPromocoes", instanceOf(Boolean.class))

                // Validate "produtos"
                .body("produtos.disponivel", everyItem(instanceOf(Boolean.class)))
                .body("produtos.id", everyItem(anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class))))
                .body("produtos.nome", everyItem(not(equalTo(""))))
                .body("produtos.preco", everyItem(anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class))))

                // Validate "recomendacoes"
                .body("recomendacoes.preco", everyItem(anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class))))
                .body("recomendacoes.produto", everyItem(not(equalTo(""))))

                // Validate "ultimaCompra"
                .body("ultimaCompra.data", matchesPattern("^\\d{4}-\\d{2}-\\d{2}$"))
                .body("ultimaCompra.produto", not(equalTo("")))
                .body("ultimaCompra.valor", anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class)))

                // Validate "usuario"
                .body("usuario.email", matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$"))
                .body("usuario.idade", anyOf(instanceOf(Integer.class), instanceOf(Float.class), instanceOf(Double.class)))
                .body("usuario.nome", not(equalTo("")));
    }
}
