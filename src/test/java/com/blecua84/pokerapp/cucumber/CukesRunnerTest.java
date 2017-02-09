package com.blecua84.pokerapp.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Clase de ejecución de las pruebas BDD.
 *
 * @author josejavier.blecua
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CukesRunnerTest {
}
