package de.codecentric.psd.worblehat;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("de/codecentric/psd/worblehat/features")
@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value =
        "pretty, "
            + "html:target/cucumber/index.html,"
            + "junit:target/cucumber.xml,"
            + "json:target/cucumber-report.json")
public class AcceptanceTestsIT {}
