package UK.GOV.BEIS.SCTDB.steps.adminportal;

import UK.GOV.BEIS.SCTDB.pages.adminportal.AddSubsidyPage;
import UK.GOV.BEIS.SCTDB.pages.adminportal.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddSubsidy_Steps {

    AddSubsidyPage Obj_AddSubsidy;

    @Given("I enter a valid {string} from {string}")
    public void i_enter_a_valid_emailID(String TDID, String SheetName) {

        Obj_AddSubsidy.enterValues(TDID,SheetName);
    }

}
