package UK.GOV.BEIS.SCTDB.steps.searchportal;

import UK.GOV.BEIS.SCTDB.pages.searchportal.RecipientPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.GrantingDatePage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SpendingSectorPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.TypesPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.ObjectivePage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SearchHomePage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SearchResultsPage;

import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.HashMap;

public class UserSearch_Steps {

    SearchHomePage Obj_SearchHomePage;
    RecipientPage obj_RecipientPage;
    ObjectivePage Obj_ObjectivePage;
    SpendingSectorPage Obj_SpendingSectorPage;
    TypesPage obj_TypesPage;
    GrantingDatePage Obj_GrantingDatePage;
    SearchResultsPage Obj_SearchResultsPage;
    HashMap<String,String> TestData;


    @Given("I navigate to Search Portal")
    public void iNavigateToSearchPortal() {
        Serenity.initializeTestSession();
        Obj_SearchHomePage.open();
        Obj_SearchHomePage.Start();
    }

    @Given("I have {string} from {string}")
    public void iHaveFrom(String TDID, String SheetName) {

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

    @When("I enter the search criteria")
    public void iEnterTheSearchCriteria() {

        obj_RecipientPage.SearchByRecipient(TestData.get("Recipient"));
        Obj_ObjectivePage.SearchByPurpose(TestData.get("Purpose"), TestData.get("Other Purpose"));
        Obj_ObjectivePage.proceed();
        Obj_SpendingSectorPage.SearchBySector(TestData.get("Sector"));
        Obj_SpendingSectorPage.proceed();
        obj_TypesPage.SearchBySubsidyType(TestData.get("Type"), TestData.get("Other Type"));
        obj_TypesPage.proceed();
        Obj_GrantingDatePage.SearchByDate(TestData.get("From"), TestData.get("To"));
        Obj_GrantingDatePage.proceed();
        Obj_SearchResultsPage.refineFilter(TestData);
    }

    @Then("I will be able to get the relevant search results")
    public void iWillBeAbleToGetTheRelevantSearchResults() {

        String ResultColumnIndex="", ExpectedValues="";

        switch (TestData.get("Validate").toLowerCase()){
            case "recipient":
                ExpectedValues = TestData.get("Expected Recipient");
                ResultColumnIndex="1";
                break;
            case "title":
                ExpectedValues = TestData.get("Expected Title");
                ResultColumnIndex="2";
                break;
            case "amount":
                ExpectedValues = TestData.get("Expected Amount");
                ResultColumnIndex="3";
                break;
            case "purpose":
                ExpectedValues = TestData.get("Expected Purpose");
                ResultColumnIndex="4";
                break;
            case "type":
                ExpectedValues = TestData.get("Expected Type");
                ResultColumnIndex="5";
                break;
            case "sector":
                ExpectedValues = TestData.get("Expected Sector");
                ResultColumnIndex="6";
                break;
            case "date":
                ResultColumnIndex="7";
                String From= TestData.get("Expected From").toLowerCase();
                String To= TestData.get("Expected To").toLowerCase();
                if(From.contentEquals("_blank")){
                    From="1960-01-01";
                }
                if(To.contentEquals("_blank")){
                    To=LocalDate.now().toString();
                }
                ExpectedValues = From+"|"+To;
                break;
            case "sort":
                ResultColumnIndex="7";
                String SortBy=TestData.get("Expected Sort").toLowerCase();
                if(SortBy.startsWith("recipient"))
                {
                ResultColumnIndex="1";
                }
                else if(SortBy.startsWith("amount"))
                {
                ResultColumnIndex="3";
                }
                ExpectedValues = SortBy.split("\\|")[1];
                break;
            case "_blank":
                ExpectedValues = "_blank";
                break;
            case "no result":
                ResultColumnIndex="0";
                ExpectedValues = "no result";
                break;
            default:
                Assert.fail("Incorrect Column to Validate");
        }

        if(ExpectedValues.contentEquals("_blank"))
      {
          Assert.fail("Incorrect column name/values given to validate");
      }
      else
      {
          Obj_SearchResultsPage.validateSearchResults(ResultColumnIndex,ExpectedValues);
      }

    }


}