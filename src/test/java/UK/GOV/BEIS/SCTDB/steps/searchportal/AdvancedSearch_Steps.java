package UK.GOV.BEIS.SCTDB.steps.searchportal;

import UK.GOV.BEIS.SCTDB.pages.searchportal.GrantingDatePage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.ObjectivePage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.RecipientPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SearchHomePage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SearchResultsPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SpendingSectorPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.TypesPage;
import UK.GOV.BEIS.SCTDB.utilities.BrowserStackSerenityDriver;
import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.HashMap;

public class AdvancedSearch_Steps {

    SearchHomePage Obj_SearchHomePage;
    RecipientPage obj_RecipientPage;
    ObjectivePage Obj_ObjectivePage;
    SpendingSectorPage Obj_SpendingSectorPage;
    TypesPage obj_TypesPage;
    GrantingDatePage Obj_GrantingDatePage;
    HashMap<String,String> TestData;
    String ScenarioData;


   /* @After
    public void update(Scenario scenario){
        String DriverType = new Reusable().getProperty("webdriver.driver");
        if(DriverType.contentEquals("provided")){
        new BrowserStackSerenityDriver().updateBS(Obj_SearchHomePage.getBSID(),scenario.getStatus().toString(), ScenarioData);}

    }*/

    @Given("I am on the Public User Search Portal")
    public void iNavigateToSearchPortal() {
        Serenity.initializeTestSession();
        Obj_SearchHomePage.open();
        Obj_SearchHomePage.Start();
    }

    @Given("I have test data {string} from {string}")
    public void iHaveFrom(String TDID, String SheetName) {
        ScenarioData=TDID;
        TestData = new Reusable().readExcelDataNew("./src/test/resources/data/sample.xlsx",SheetName,TDID);
        if(TestData.isEmpty()){
            Assert.fail("There is no matching TDID in the datasheet");
        }

        /*new Thread(() -> {
            TestData = new Reusable().readExcelDataNew("./src/test/resources/data/sample.xlsx",SheetName,TDID);
            if(TestData.isEmpty()){
                Assert.fail("There is no matching TDID in the datasheet");
            }
        }).start();*/
    }

    @When("I have completed my entries into the search fields")
    public void iEnterTheSearchCriteria() {
        obj_RecipientPage.SearchByRecipient(TestData.get("Recipient"));
        Obj_ObjectivePage.SearchByPurpose(TestData.get("Purpose"), TestData.get("Other Purpose"),"");
        Obj_ObjectivePage.proceed();
        Obj_SpendingSectorPage.SearchBySector(TestData.get("Sector"),"");
        Obj_SpendingSectorPage.proceed();
        obj_TypesPage.SearchBySubsidyType(TestData.get("Type"), TestData.get("Other Type"),"");
        obj_TypesPage.proceed();
        Obj_GrantingDatePage.SearchByDate(TestData.get("From"), TestData.get("To"));
    }

    @When("I want to return to check my previous inputs")
    public void iWantToReturnToCheckMyPreviousInputs() {
        Obj_GrantingDatePage.previous();
        obj_TypesPage.previous();
        Obj_SpendingSectorPage.previous();
        Obj_ObjectivePage.previous();
    }

    @Then("I want to be able to see my previous entries so I can keep track of what I have entered.")
    public void iWantToBeAbleToSeeMyPreviousEntriesSoICanKeepTrackOfWhatIHaveEntered() {
        obj_RecipientPage.validateSelections(TestData.get("Recipient"));
        Obj_ObjectivePage.validateSelections(TestData.get("Purpose"), TestData.get("Other Purpose"));
        Obj_ObjectivePage.proceed();
        Obj_SpendingSectorPage.validateSelections(TestData.get("Sector"));
        Obj_SpendingSectorPage.proceed();
        obj_TypesPage.validateSelections(TestData.get("Type"), TestData.get("Other Type"));
    }
}