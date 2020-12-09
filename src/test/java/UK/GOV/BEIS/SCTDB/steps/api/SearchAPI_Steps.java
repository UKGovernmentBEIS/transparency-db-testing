package UK.GOV.BEIS.SCTDB.steps.api;

import UK.GOV.BEIS.SCTDB.pages.api.API;
import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class SearchAPI_Steps {
    Reusable ReusableObj;

    @Steps
    API APIobj;

    @Given("a POST {string}")
    public void i_have_a_valid_POST_endpoint(String EndPoint) {
        APIobj.setEndPoint(EndPoint);
    }


    @When("I send a request with Empty JSON body")
    public void iSendProperRequestWithEmptyPayload() {
        APIobj.getResponseForPOST("");
    }

    @When("I send a request with JSON body from {string}")
    public void iSendProperRequestWithPayload(String PayloadPath) {
                    APIobj.getResponseForPOST(PayloadPath);
            }

    @Then("I will be getting the expected {int}")
    public void iWillBeGettingExpectedStatusCode(int StatusCode) {
        APIobj.validateStatusCode(StatusCode);
    }

}
