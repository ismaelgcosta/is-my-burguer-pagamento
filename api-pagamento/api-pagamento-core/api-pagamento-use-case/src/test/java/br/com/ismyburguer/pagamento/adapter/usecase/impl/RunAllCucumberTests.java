package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class RunAllCucumberTests {
}