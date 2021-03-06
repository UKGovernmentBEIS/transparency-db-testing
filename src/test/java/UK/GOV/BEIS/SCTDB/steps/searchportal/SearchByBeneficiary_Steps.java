package UK.GOV.BEIS.SCTDB.steps.searchportal;

import UK.GOV.BEIS.SCTDB.pages.searchportal.RecipientPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.GrantingDatePage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.TypesPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.ObjectivePage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SearchHomePage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SearchResultsPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SpendingSectorPage;
import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;

public class SearchByBeneficiary_Steps {

    SearchHomePage Obj_SearchHomePage;
    RecipientPage obj_RecipientPage;
    ObjectivePage Obj_ObjectivePage;
    SpendingSectorPage Obj_SpendingSectorPage;
    TypesPage obj_TypesPage;
    GrantingDatePage Obj_GrantingDatePage;
    SearchResultsPage Obj_SearchResultsPage;
    ArrayList<Object> TestData;

    @Given("a Public User is on the Public User Search Portal,")
    public void aPublicUserIsOnThePublicUserSearchPortal() {
        Obj_SearchHomePage.open();
        Obj_SearchHomePage.Start();
    }
    @When("the Public User specifies a Beneficiary name {string} from {string}")
    public void thePublicUserSpecifiesABeneficiaryNameFrom(String TDID, String SheetName) {
        TestData = new Reusable().readExcelData("./src/test/resources/data/sample.xlsx",SheetName,TDID);
        if(TestData.isEmpty()){
            Assert.fail("There is no matching TDID in the datasheet");
        }

        obj_RecipientPage.SearchByRecipient(TestData.get(0).toString());
        Obj_ObjectivePage.SearchByPurpose(TestData.get(1).toString(), TestData.get(2).toString(),"");
        Obj_SpendingSectorPage.SearchBySector(TestData.get(3).toString(),"");
        obj_TypesPage.SearchBySubsidyType(TestData.get(4).toString(), TestData.get(5).toString(),"");
        Obj_GrantingDatePage.SearchByDate(TestData.get(6).toString(), TestData.get(7).toString());
    }

    @Then("a search is returned that filters by the selected Beneficiary name.")
    public void aSearchIsReturnedThatFiltersByTheSelectedBeneficiaryName() {
        int ColumnIndex =-1;
        String ResultColumnIndex="", ExpectedValues="";

        switch (TestData.get(8).toString().toLowerCase()){
            case "name":
                ExpectedValues = TestData.get(9).toString();
                ResultColumnIndex="1";
                break;
            case "title":
                ExpectedValues = TestData.get(10).toString();
                ResultColumnIndex="2";
                break;
            case "amount":
                ExpectedValues = TestData.get(11).toString();
                ResultColumnIndex="3";
                break;
            case "objective":
                ExpectedValues = TestData.get(12).toString();
                ResultColumnIndex="4";
                break;
            case "instrument":
                ExpectedValues = TestData.get(13).toString();
                ResultColumnIndex="5";
                break;
            case "sector":
                ExpectedValues = TestData.get(14).toString();
                ResultColumnIndex="6";
                break;
            case "date":
                ResultColumnIndex="7";
                if(TestData.get(15).toString().contentEquals("_blank")){
                    ExpectedValues = "T"+TestData.get(16).toString();
                }
                else if(TestData.get(16).toString().contentEquals("_blank")){
                    ExpectedValues = "F"+TestData.get(15).toString();
                }
                else if(!(TestData.get(15).toString().contentEquals("_blank")) &&!(TestData.get(16).toString().contentEquals("_blank"))){
                    ExpectedValues = TestData.get(15).toString()+"|"+TestData.get(16).toString();
                }
                else{
                    ExpectedValues ="_blank";
                }
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

        if(!(ExpectedValues.contentEquals("_blank")))
            Obj_SearchResultsPage.validateSearchResults(ResultColumnIndex,ExpectedValues);
    }


}