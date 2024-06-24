package com.example;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // caminho para o arquivo .feature
        glue = "com.example", // pacote onde estão as classes de step definitions
        plugin = {"pretty", "json:target/cucumber-report.json"},
        publish = true // habilitar a publicação do relatório
)
public class RunCucumberTest {
}
