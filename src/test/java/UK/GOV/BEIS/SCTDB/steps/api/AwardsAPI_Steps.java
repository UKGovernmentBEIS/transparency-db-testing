package UK.GOV.BEIS.SCTDB.steps.api;

import UK.GOV.BEIS.SCTDB.pages.api.API;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class AwardsAPI_Steps {
    @Steps
    API APIobj;

    @Given("an {string} to fetch all awards")
    public void i_have_a_valid_POST_endpoint(String EndPoint) {
        APIobj.setEndPoint(EndPoint);
    }

    @When("I send a GET request")
    public void i_send_a_request() {
        APIobj.getResponseForGET();
    }

    @Then("I will be getting the successful {int} for GET Awards")
    public void iWillBeGettingExpectedStatusCode(int StatusCode) {
        APIobj.validateStatusCode(StatusCode);
    }

}
