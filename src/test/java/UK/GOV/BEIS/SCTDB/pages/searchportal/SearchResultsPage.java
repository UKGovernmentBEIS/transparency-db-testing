package UK.GOV.BEIS.SCTDB.pages.searchportal;

import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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

    @FindBy(xpath = "//button[contains(text(),'Purpose')]")
    @CacheLookup
    WebElementFacade Purpose;

    @FindBy(xpath = "//button[contains(text(),'Sector')]")
    @CacheLookup
    WebElementFacade Sector;

    @FindBy(xpath = "//button[contains(text(),'Type')]")
    @CacheLookup
    WebElementFacade Type;

    @FindBy(xpath = "//button[contains(text(),'Open all')]")
    WebElementFacade Open;

    // To get search result column values
    public List<String> getTableValues(String ColumnIndex){

        List<String> ColumnValues = new ArrayList<>();
        boolean todo = true;
        int expectedResultCount = Integer.parseInt($("//label/b").getText());
        int resultsPerPage = Integer.parseInt($("//a[contains(@aria-label,'records per page')][contains(@class,'active')]").getText());
        int expectedPages=(expectedResultCount+(resultsPerPage-1))/resultsPerPage;
        int page =1;

        while(todo)
        {
            if(ColumnIndex.equals("7")){
                DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd MMMM uuuu");
                for (WebElement e : getDriver().findElements(By.xpath("//tbody//tr/td["+ColumnIndex+"]"))) {
                ColumnValues.add(LocalDate.parse(e.getText(),DateFormat).toString());
                }
            }

        else{

                new WebDriverWait(getDriver(), 10).until((ExpectedCondition<Boolean>) wd ->
                        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

              /*  for (WebElement e : getDriver().findElements(By.xpath("//tbody//tr/td[" + ColumnIndex + "]"))) {
                    ColumnValues.add(e.getText());
                }*/
                    int totalRecords = findAll(By.xpath("//tbody//tr/td[" + ColumnIndex + "]")).size();
                    System.out.println("totalRecords: "+totalRecords);
                    for (int rowNum=1;rowNum<=totalRecords;rowNum++){
                        System.out.println("Current Row: "+rowNum);
                        ColumnValues.add(withTimeoutOf(Duration.ofSeconds(5))
                                .find(By.xpath("//tbody//tr["+rowNum+"]/td[" + ColumnIndex + "]"))
                                .getText());
                    }

        }

            if(findAll(By.xpath("//a[contains(text(),'Next Page')]")).size()>0) {
                $("//a[contains(text(),'Next Page')]").click();
                page++;
                //waitFor(ExpectedConditions.urlMatches("\\/\\?*page\\="));
                waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@id='paginationlink"+page+"'][contains(@class,'active')]")));

                //waitFor(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@class='pagination-label']/span[1]"))));
            }
            else{
                todo = false;
            }
        }
    return ColumnValues;
    }


    //Validate Sort
    public void sortResults(String ColumnIndex, String SortOrder){

        List<String> Actual = getTableValues(ColumnIndex);

        if(ColumnIndex.contentEquals("7")){
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
        }
        else if(ColumnIndex.contentEquals("5")){
            List<LocalDate> Dates= new ArrayList<>();
            DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd MMMM uuuu");
            for(String s: Actual){
                Dates.add(LocalDate.parse(s,DateFormat));
            }

            List<LocalDate> tempDates = new ArrayList<>(List.copyOf(Dates));
            tempDates.sort(Comparator.naturalOrder());
            if (!SortOrder.contentEquals("asc")) {
                Collections.reverse(tempDates);
            }
            System.out.println("Actual: " + Dates + "\nExpected: " + tempDates);
            if(!Dates.equals(tempDates)){
                Assert.fail(Dates + " is not sorted properly");
            }
        }
        else {
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

    withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//form/div/button"));

    //Validate if no results are displayed
    if(findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size()<1) {
        $("//a[contains(text(),'Show filters')]").click();
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//a[contains(text(),'Hide filters')]"));
        Open.click();

        if (!TestData.get("Purpose Filter").contentEquals("_BLANK")) {
            //Purpose.click();
            Obj_ObjectivePage.SearchByPurpose(TestData.get("Purpose Filter"), TestData.get("Other Purpose Filter"),"refine");
        }
        if (!TestData.get("Sector Filter").contentEquals("_BLANK")) {
            //Sector.click();
            Obj_SpendingSectorPage.SearchBySector(TestData.get("Sector Filter"),"refine");
        }
        if (!TestData.get("Type Filter").contentEquals("_BLANK")) {
            //Type.click();
            obj_TypesPage.SearchBySubsidyType(TestData.get("Type Filter"), TestData.get("Other Type Filter"),"refine");
        }

        if (findAll("//button[contains(text(),'Update results')]").size() > 0) {
            $("//button[contains(text(),'Update results')]").click();
        }

        //waitFor(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath("//label/b"))));
        waitFor(ExpectedConditions.urlMatches("\\/updateresults"));
    }
    Serenity.takeScreenshot();
    }


    public void validateDetailsPage(String ColumnIndex, String Values) {
        HashMap<String, String> TestData;
        List<String> MeasureDetails = new ArrayList<>();
        List<String> AwardDetails = new ArrayList<>();
        List<String> Expected = new ArrayList<>();

       /* waitFor(ExpectedConditions.urlMatches("\\/*results"));
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1"));*/

        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//form/button"));

        if(findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size()>0){
            if(!Values.contentEquals("no result")){
                Assert.fail("No results found for the search criteria");
            }
        }

        else if(findAll(By.xpath("//h1[contains(text(),'Search results')]")).size()>0)


            if (ColumnIndex.contentEquals("1")) {
            $("//tr/td[" + ColumnIndex + "]/a[contains(text(),'" + Values + "')]").click();
            TestData = new Reusable().readExcelDataNew("./src/test/resources/data/sample.xlsx", "AwardDetails", Values);
            if (TestData.isEmpty()) {
                Assert.fail("There is no matching TDID in the datasheet");
            }
                waitFor(ExpectedConditions.visibilityOf($("//h1[contains(text(),'Subsidy awards details')]")));
                Assert.assertEquals("Title Validation", "Subsidy awards details", $("//h1").getText());
                for (WebElement e : getDriver().findElements(By.xpath("//dd"))) {
                    AwardDetails.add(e.getText());
                }
                Serenity.takeScreenshot();

                Expected.add(TestData.get("SC Number"));
                Expected.add(TestData.get("Award Number").replaceAll("\\.*0*$", ""));
                Expected.add(TestData.get("Subsidy Scheme"));
                Expected.add(TestData.get("Subsidy Purpose"));
                Expected.add(TestData.get("Subsidy Type"));
                if(!TestData.get("Subsidy Amount Range").contentEquals("NA")){
                Expected.add(TestData.get("Subsidy Amount Range"));
                }
                if(!TestData.get("Subsidy Amount Exact").contentEquals("NA")){
                Expected.add(TestData.get("Subsidy Amount Exact"));
                }
                Expected.add(TestData.get("Granting Authority"));
                Expected.add(TestData.get("Date Awarded"));
                Expected.add(TestData.get("Beneficiary Name"));

                System.out.println(TestData.get("Organisation Size"));
                Expected.add(TestData.get("Organisation Size"));
                Expected.add(TestData.get("National ID Type"));
                System.out.println(TestData.get("National ID"));
                Expected.add(TestData.get("National ID"));
                Expected.add(TestData.get("Goods/Services Filter"));
                Expected.add(TestData.get("Spending Region"));
                Expected.add(TestData.get("Spending Sector"));

                System.out.println("Actual: " + AwardDetails + "\nExpected: " + Expected);


                if (!AwardDetails.equals(Expected)){
                    Assert.fail("Values are not matching" + AwardDetails);
                }

               /* List<String> tempEx = List.copyOf(Expected);
                Expected.removeAll(AwardDetails);
                AwardDetails.removeAll(tempEx);

                if (!Expected.isEmpty()) {
                    Assert.fail(Expected + " is not present in the Details");
                }
                if (!AwardDetails.isEmpty()) {
                    Assert.fail(AwardDetails + " is present additionally in the Details");
                }*/



        }
        else{

        $("//tr/td[" + ColumnIndex + "]/a[contains(text(),'" + Values + "')]").click();
        TestData = new Reusable().readExcelDataNew("./src/test/resources/data/sample.xlsx", "MeasureDetails", Values);
        if (TestData.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
                Serenity.takeScreenshot();

            Expected.add(TestData.get("SC Number"));
            Expected.add(TestData.get("Region"));
            Expected.add(TestData.get("Purpose"));
            Expected.add(TestData.get("Sector"));
            Expected.add(TestData.get("Legal Basis"));
            Expected.add(TestData.get("GA Websubsidy Link"));
            Expected.add(TestData.get("Type"));
            Expected.add(TestData.get("Case Type"));
            Expected.add(TestData.get("Duration"));
            Expected.add(TestData.get("Date"));

        {
            waitFor(ExpectedConditions.visibilityOf($("//h1[contains(text(),'Subsidy scheme details')]")));
            Assert.assertEquals("Title Validation", "Subsidy scheme details", $("//h1").getText());
            for (WebElement e : getDriver().findElements(By.xpath("//dd"))) {
                if(!e.getText().contentEquals(Values))
                MeasureDetails.add(e.getText());
            }

            System.out.println("Actual: " + MeasureDetails + "\nExpected: " + Expected);
            //List<String> tempEx = List.copyOf(Expected);

            if (!MeasureDetails.equals(Expected)){
                Assert.fail("Values are not matching" + MeasureDetails);

            }
           /* Expected.removeAll(MeasureDetails);
            MeasureDetails.removeAll(tempEx);

            if (!Expected.isEmpty()) {
                Assert.fail(Expected + " is not present in the Details");
            }
            if (!MeasureDetails.isEmpty()) {
                Assert.fail(MeasureDetails + " is present additionally in the Details");
            }*/

        }

    }
    }

    public void validatePagination() {
        if(findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size()>0){
            Assert.fail("No results found for the search criteria");
        }
        else
        {
            int expectedResultCount = Integer.parseInt($("//label/b").getText());
            int resultsPerPage = Integer.parseInt($("//select/option[@selected]").getText());

            int expectedPages=(expectedResultCount+(resultsPerPage-1))/resultsPerPage;

            int results = Integer.parseInt($("//tbody/tr").getText());
            int actualPages = findAll(By.xpath("//a[contains(@class, 'round btn-page')]")).size();

        }

    }

    public void validateSearchResults(String ColumnIndex, String Values) {

        //waitFor(ExpectedConditions.urlMatches("\\/*results"));
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//form/button"));
        Serenity.takeScreenshot();

        if(findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size()>0){
            if(!Values.contentEquals("no result")){
                Assert.fail("No results found for the search criteria");
            }
        }

        else if(findAll(By.xpath("//h1[contains(text(),'Search results')]")).size()>0)
        {
            int counter =1;
        //To initiate Sort Validation
        if (Values.contentEquals("asc")) {
           /* while (findAll(By.xpath("//th[" + ColumnIndex + "]/a/img[contains(@id,'_uparrow')]")).size() < 1) {
                $("//tr/th[" + ColumnIndex + "]/a/img").click();
                counter++;
                if(counter>2){
                    break;
            }
            }*/

            if ((findAll(By.xpath("//th[" + ColumnIndex + "]/a/img[contains(@aria-hidden,'not sorted')]")).size() > 0) ||
                    (findAll(By.xpath("//th[" + ColumnIndex + "]/a/img[contains(@aria-hidden,'Descending order')]")).size() > 0)){

                $("//tr/th[" + ColumnIndex + "]/a/img").click();
            }
            waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[" + ColumnIndex + "]/a/img[contains(@aria-hidden,'Ascending order')]")));
            sortResults(ColumnIndex, Values);

        } else if (Values.contentEquals("dsc")) {
            /*while (findAll(By.xpath("//th[" + ColumnIndex + "]/a/img[contains(@id,'_downarrow')]")).size() < 1) {
                $("//tr/th[" + ColumnIndex + "]/a/img").click();
                waitFor(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr/th[" + ColumnIndex + "]/a/img"))));
                counter++;
                if(counter>2){
                    break;
                }
            }*/
            if ((findAll(By.xpath("//th[" + ColumnIndex + "]/a/img[contains(@aria-hidden,'not sorted')]")).size() > 0)){
                $("//tr/th[" + ColumnIndex + "]/a/img").click();
                waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[" + ColumnIndex + "]/a/img[contains(@aria-hidden,'Ascending order')]")));
            }

            if ((findAll(By.xpath("//th[" + ColumnIndex + "]/a/img[contains(@aria-hidden,'Ascending order')]")).size() > 0)){
                $("//tr/th[" + ColumnIndex + "]/a/img").click();
            }
            waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[" + ColumnIndex + "]/a/img[contains(@aria-hidden,'Descending order')]")));
            sortResults(ColumnIndex, Values);
        } else {
            List<String> Expected = new ArrayList<>(Arrays.asList(Values.split("\\|")));
            List<String> Actual = new ArrayList<>(getTableValues(ColumnIndex));

                /*Collections.sort(Expected);
                Collections.sort(Actual);
                System.out.println("Actual: " + Actual + "\nExpected: " + Expected);*/

            //To Check if the result dates are present within the expected date range
            if (ColumnIndex.equals("5")) {
                DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd MMMM uuuu");
                LocalDate ExpectedFrom = LocalDate.parse(Expected.get(0));
                LocalDate ExpectedTo = LocalDate.parse(Expected.get(1));

                LocalDate ResultDate;
                for (String Date : Actual) {
                    System.out.println(Date);
                    ResultDate = LocalDate.parse(Date, DateFormat);
                    if (ResultDate.isBefore(ExpectedFrom) || ResultDate.isAfter(ExpectedTo)) {
                        Assert.fail(ResultDate + " is not present in the expected date range (" + ExpectedFrom + " - " + ExpectedTo + ")");
                    }
                }

            }

            //To Check if the actual values match the expected
            else {
                List<String> tempEx = List.copyOf(Expected);
                System.out.println("Actual: " + Actual + "\nExpected: " + Expected);

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
