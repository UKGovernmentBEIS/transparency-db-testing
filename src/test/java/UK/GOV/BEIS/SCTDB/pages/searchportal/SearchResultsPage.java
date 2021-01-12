package UK.GOV.BEIS.SCTDB.pages.searchportal;

import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import UK.GOV.BEIS.SCTDB.pages.searchportal.SpendingSectorPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.TypesPage;
import UK.GOV.BEIS.SCTDB.pages.searchportal.ObjectivePage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SearchResultsPage extends PageObject {

    ObjectivePage Obj_ObjectivePage;
    SpendingSectorPage Obj_SpendingSectorPage;
    TypesPage obj_TypesPage;

    @FindBy(xpath = "//button[contains(text(),'Try another search')]")
    @CacheLookup
    WebElementFacade btn_NewSearch;

    @FindBy(xpath = "//p[@class='pagination-label']/span[3]")
    @CacheLookup
    WebElementFacade TotalResults;

    @FindBy(xpath = "//button[contains(text(),'Objectives')]")
    @CacheLookup
    WebElementFacade Objectives;

    @FindBy(xpath = "//button[contains(text(),'Sector')]")
    @CacheLookup
    WebElementFacade Sector;

    @FindBy(xpath = "//button[contains(text(),'Instrument')]")
    @CacheLookup
    WebElementFacade Type;

    // To get search result column values
    public List<String> getTableValues(String ColumnIndex){

        List<String> ColumnValues = new ArrayList<>();
        boolean todo = true;
        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd MMMM uuuu");

        while(todo)
        {
            if(ColumnIndex.equals("7")){
                for (WebElement e : getDriver().findElements(By.xpath("//tbody//tr/td["+ColumnIndex+"]"))) {
                ColumnValues.add(LocalDate.parse(e.getText(),DateFormat).toString());
                }
            }

        else{
                for (WebElement e : getDriver().findElements(By.xpath("//tbody//tr/td[" + ColumnIndex + "]"))) {
                    ColumnValues.add(e.getText());
                }
        }

            if(findAll(By.xpath("//a[contains(text(),'Next Page')]")).size()>0) {
                $("//a[contains(text(),'Next Page')]").click();
            }else{
                todo = false;
            }
        }
    return ColumnValues;
    }


    //Validate Sort
    public void sortResults(String ColumnIndex, String SortOrder){

        List<String> Actual = getTableValues(ColumnIndex);

        if(ColumnIndex.contentEquals("3")){
            List<Integer> Amounts= new ArrayList<>();
            for(String s : Actual){
                Amounts.add(Integer.parseInt(s.substring(1).split(" - ")[0].replace(",","")));
            }
            List<Integer> tempNum = new ArrayList<>(List.copyOf(Amounts));
            Collections.sort(tempNum);
            if (SortOrder.contentEquals("dsc")) {
                Collections.reverse(tempNum);
            }System.out.println("Actual: " + Amounts + "\nExpected: " + tempNum);
            if(!Amounts.equals(tempNum)){
                Assert.fail(Actual + " is not sorted properly");
            }


        } else {
            List<String> tempString = new ArrayList<>(List.copyOf(Actual));
            tempString.sort(String.CASE_INSENSITIVE_ORDER);
            if (!SortOrder.contentEquals("asc")) {
                Collections.reverse(tempString);
            }
            System.out.println("Actual: " + Actual + "\nExpected: " + tempString);
            if(!Actual.equals(tempString)){
            Assert.fail(Actual + " is not sorted properly");
             }
        }
    }
public void refineFilter(HashMap<String,String> TestData){

    //Validate if no results are displayed
    if(findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size()<1) {

        if (!(TestData.get("Purpose Filter").contentEquals("_BLANK")) ||
                !(TestData.get("Sector Filter").contentEquals("_BLANK")) || !(TestData.get("Type Filter").contentEquals("_BLANK"))) {
            $("//button[contains(text(),'Show filters')]").click();
        }

        if (!TestData.get("Purpose Filter").contentEquals("_BLANK")) {
            Objectives.click();
            Obj_ObjectivePage.SearchByPurpose(TestData.get("Purpose Filter"), TestData.get("Other Purpose Filter"));
        }
        if (!TestData.get("Sector Filter").contentEquals("_BLANK")) {
            Sector.click();
            Obj_SpendingSectorPage.SearchBySector(TestData.get("Sector Filter"));
        }
        if (!TestData.get("Type Filter").contentEquals("_BLANK")) {
            Type.click();
            obj_TypesPage.SearchBySubsidyType(TestData.get("Type Filter"), TestData.get("Other Type Filter"));
        }

        if (findAll("//button[contains(text(),'Update results')]").size() > 0) {
            $("//button[contains(text(),'Update results')]").click();
        }
    }
    }


    public void validateDetailsPage(String ColumnIndex, String Values) {
        HashMap<String, String> TestData;
        List<String> MeasureDetails = new ArrayList<>();
        List<String> AwardDetails = new ArrayList<>();

        if (ColumnIndex.contentEquals("1")) {

            TestData = new Reusable().readExcelDataNew("./src/test/resources/data/sample.xlsx", "AwardDetails", Values);
            if (TestData.isEmpty()) {
                Assert.fail("There is no matching TDID in the datasheet");
            }

            if (findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size() > 0) {
                if (!Values.contentEquals("no result")) {
                    Assert.fail("No results found for the search criteria");
                }
            } else {
                $("//tr/td[" + ColumnIndex + "]/a[contains(text(),'" + Values + "')]").click();

                Assert.assertEquals("Title Validation", "Subsidy awards details", $("//h1").getText());
                for (WebElement e : getDriver().findElements(By.xpath("//dd"))) {
                    AwardDetails.add(e.getText());
                }

                List<String> Expected = new ArrayList<>(TestData.values());
                System.out.println("Actual: " + AwardDetails + "\nExpected: " + Expected);
                List<String> tempEx = List.copyOf(Expected);
                Expected.removeAll(AwardDetails);
                AwardDetails.removeAll(tempEx);

                if (!Expected.isEmpty()) {
                    Assert.fail(Expected + " is not present in the Details");
                }
                if (!AwardDetails.isEmpty()) {
                    Assert.fail(AwardDetails + " is present additionally in the Details");
                }

            }

        }
        else{


        TestData = new Reusable().readExcelDataNew("./src/test/resources/data/sample.xlsx", "MeasureDetails", Values);
        if (TestData.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        List<String> Expected = new ArrayList<>(TestData.values());

        if (findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size() > 0) {
            if (!Values.contentEquals("no result")) {
                Assert.fail("No results found for the search criteria");
            }
        } else {
            $("//tr/td[" + ColumnIndex + "]/a[contains(text(),'" + Values + "')]").click();

            Assert.assertEquals("Title Validation", Values, $("//h1").getText());
            for (WebElement e : getDriver().findElements(By.xpath("//dd"))) {
                MeasureDetails.add(e.getText());
            }

            System.out.println("Actual: " + MeasureDetails + "\nExpected: " + Expected);
            List<String> tempEx = List.copyOf(Expected);
            Expected.removeAll(MeasureDetails);
            MeasureDetails.removeAll(tempEx);

            if (!Expected.isEmpty()) {
                Assert.fail(Expected + " is not present in the Details");
            }
            if (!MeasureDetails.isEmpty()) {
                Assert.fail(MeasureDetails + " is present additionally in the Details");
            }

        }

    }
    }

    public void validatePagination() {
        if(findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size()>0){
            Assert.fail("No results found for the search criteria");
        }
        else
        {

        }

    }

    public void validateSearchResults(String ColumnIndex, String Values) {


        if(findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size()>0){
            if(!Values.contentEquals("no result")){
                Assert.fail("No results found for the search criteria");
            }
        }
        else
        {
        //To initiate Sort Validation
        if (Values.contentEquals("asc")) {
            while (findAll(By.xpath("//th[" + ColumnIndex + "]/a/img[@id='downarrow']")).size() < 1) {
                $("//tr/th[" + ColumnIndex + "]").click();
            }
            sortResults(ColumnIndex, Values);
        } else if (Values.contentEquals("dsc")) {
            while (findAll(By.xpath("//th[" + ColumnIndex + "]/a/img[@id='uparrow']")).size() < 1) {
                $("//tr/th[" + ColumnIndex + "]").click();
            }
            sortResults(ColumnIndex, Values);
        } else {
            List<String> Expected = new ArrayList<>(Arrays.asList(Values.split("\\|")));
            List<String> Actual = new ArrayList<>(getTableValues(ColumnIndex));

                /*Collections.sort(Expected);
                Collections.sort(Actual);
                System.out.println("Actual: " + Actual + "\nExpected: " + Expected);*/

            //To Check if the result dates are present within the expected date range
            if (ColumnIndex.equals("5")) {
                LocalDate ExpectedFrom = LocalDate.parse(Expected.get(0));
                LocalDate ExpectedTo = LocalDate.parse(Expected.get(1));

                LocalDate ResultDate;
                for (String Date : Actual) {
                    ResultDate = LocalDate.parse(Date);
                    if (ResultDate.isBefore(ExpectedFrom) || ResultDate.isAfter(ExpectedTo)) {
                        Assert.fail(ResultDate + " is not present in the expected date range (" + ExpectedFrom + " - " + ExpectedTo + ")");
                    }
                }

            }

            //To Check if the actual values match the expected
            else {
                List<String> tempEx = List.copyOf(Expected);
                Expected.removeAll(Actual);
                Actual.removeAll(tempEx);

                if (!Expected.isEmpty()) {
                    Assert.fail(Expected + " is not present in the search result");
                }
                if (!Actual.isEmpty()) {
                    Assert.fail(Actual + " is present additionally in the search result");
                }
            }
        }
    }
    }

}
