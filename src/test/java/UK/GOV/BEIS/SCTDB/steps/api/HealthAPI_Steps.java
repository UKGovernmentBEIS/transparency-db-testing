package UK.GOV.BEIS.SCTDB.steps.api;

import UK.GOV.BEIS.SCTDB.pages.api.API;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class HealthAPI_Steps {
    @Steps
    API APIobj;

    @Given("an {string} to check the health status")
    public void i_have_a_valid_POST_endpoint(String EndPoint) {
        APIobj.setEndPoint(EndPoint);
    }

    @When("I send a GET Health request")
    public void i_send_a_request() {
        APIobj.getResponseForGET();
    }

    @Then("I will be getting the successful {int} for GET Health")
    public void iWillBeGettingExpectedResponse(int StatusCode) {
        APIobj.validateStatusCode(StatusCode);
    }
}
