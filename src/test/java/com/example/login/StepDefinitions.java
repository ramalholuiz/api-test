package com.example.login;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class StepDefinitions {
    private String endpoint;
    private Response response;
    private String requestBody;
    private static String token;

    @Before
    public void setup() {

        RestAssured.baseURI = "https://api-desafio-qa.onrender.com";
    }

    @Given("the API endpoint is {string}")
    public void setEndpoint(String endpoint) {

        this.endpoint = RestAssured.baseURI + endpoint;
    }

    @Given("the request body is")
    public void setRequestBody(String requestBody) {

        this.requestBody = requestBody;
    }

    @When("I send a GET request")
    public void sendGETRequest() {

        response = given().when().get(endpoint);
    }

    @When("I send a POST request")
    public void sendPOSTRequest() {
        response = given()
                .contentType("application/json")
                .body(requestBody)
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();

        if (response.statusCode() == 201) {
            token = response.jsonPath().getString("data[0].form[0].cam[0].in[0].gen[0].form_token[0].in_token[0].gen_token[0].refresh[0].bearer[0].refresh_token[0].show.token");
        }
    }

    @Then("the status code is {int}")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response content type is JSON")
    public void verifyContentTypeJSON() {
        response.then().contentType("application/json");
    }

    @Then("the {string} is present")
    public void verifyBody(String key) {
        response.then().body(key, notNullValue());
    }

//    @Then("the token is present")
//    public void verifyBody() {
//        response.then().body("data[0].form[0].cam[0].in[0].gen[0].form_token[0].in_token[0].gen_token[0].refresh[0].bearer[0].refresh_token[0].show.token", notNullValue());
//    }

    @Then("the error message is {string}")
    public void verifyErrorMessage(String errorMessage) {
        response.then().body("error", equalTo(errorMessage));
    }
}
