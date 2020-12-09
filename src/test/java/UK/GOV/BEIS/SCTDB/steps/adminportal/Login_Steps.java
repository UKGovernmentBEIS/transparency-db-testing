package UK.GOV.BEIS.SCTDB.steps.adminportal;

import UK.GOV.BEIS.SCTDB.pages.adminportal.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class Login_Steps {

    LoginPage userinfo;

    @Given("I enter a valid emailID and password {string} from {string}")
    public void i_enter_a_valid_emailID(String TDID, String SheetName) {
        userinfo.open();
        userinfo.setCredentials (TDID,SheetName);
    }
    @When("^I click on SignIn")
    public void i_click_on_SignIn () {
        userinfo.signIn();
    }

    @Then("I will be able to login successfully")
    public void i_will_be_able_to_login_successfully () {
        userinfo.verifyLogin();
}
}
