package com.example.json1;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/json1", // caminho para o arquivo .feature
        glue = "com.example.json1", // pacote onde estão as classes de step definitions
        plugin = {"pretty", "json:target/cucumber-report.json"},
        publish = true // habilitar a publicação do relatório
)
// para rodar todos os testes
//    mvn test
//para rodar apenas o teste RunJson1Test
//    mvn test -Dtest=com.example.json1.RunJson1Test
public class RunJson1Test {
}