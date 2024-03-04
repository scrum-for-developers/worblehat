package de.codecentric.psd.worblehat;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    stepNotifications = true,
    plugin = {
      "pretty",
      "html:target/cucumber/index.html",
      "junit:target/cucumber.xml",
      "json:target/cucumber-report.json"
    })
public class AcceptanceTestsIT {}
