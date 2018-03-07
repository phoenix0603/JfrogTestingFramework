package stepdefinition;

import clients.restClients;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import static helpers.globalValues.artifactoryURL;

public class metadataFileCalculation {
    restClients restClients = new restClients();

    @Given("^Test is created$")
    public void testIsCreated() throws Throwable {
       System.out.println("Created.");
    }

    @Then("^Test is passed$")
    public void testIsPassed() throws Throwable {
        System.out.println("Passed.");
    }

    @Given("^Artifactory instance is UP$")
    public void artifactoryInstanceIsUP() throws Throwable {
       Assert.assertTrue(restClients.is200OnGetRequest(artifactoryURL+"/api/system/version"));
    }
}
