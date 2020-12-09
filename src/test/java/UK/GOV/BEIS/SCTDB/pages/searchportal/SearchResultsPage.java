package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchResultsPage extends PageObject {

    @FindBy(xpath = "//button[contains(text(),'Try another search')]")
    @CacheLookup
    WebElementFacade btn_NewSearch;

    @FindBy(xpath = "//p[@class='pagination-label']/span[3]")
    @CacheLookup
    WebElementFacade TotalResults;


    // To get search result column values
    public List<String> getTableValues(String ColumnIndex){

        List<String> ColumnValues = new ArrayList<>();
        boolean todo = true;
        //DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd MMMM uuuu");

        while(todo)
        {
            if(ColumnIndex.equals("7")){
                for (WebElement e : getDriver().findElements(By.xpath("//tbody//tr/td["+ColumnIndex+"]"))) {
                ColumnValues.add(LocalDate.parse(e.getText()).toString());
                }
            }

        else{
            for (WebElement e : getDriver().findElements(By.xpath("//tbody//tr/td["+ColumnIndex+"]"))) {
                ColumnValues.add(e.getText().substring(1).split(" - ")[0].trim());
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
    public void sortResults(List<String> Actual, String SortOrder){
        List<String> tempAsc = new ArrayList<>(List.copyOf(Actual));

        if(SortOrder.contentEquals("asc"))
        {
            tempAsc.sort(String.CASE_INSENSITIVE_ORDER);
            System.out.println("Actual: " + Actual + "\nExpected: " + tempAsc);
            if(!Actual.equals(tempAsc)){
            Assert.fail(Actual + " is not sorted properly in Ascending order");
             }
        }
        else {
            tempAsc.sort(String.CASE_INSENSITIVE_ORDER);
            Collections.reverse(tempAsc);
            System.out.println("Actual: " + Actual + "\nExpected: " + tempAsc);
            if(!Actual.equals(tempAsc)) {
                Assert.fail(Actual + " is not sorted properly in Descending order");
            }
        }

    }

    public void validateSearchResults(String ColumnIndex, String Values){

        //Validate if no results are displayed
        if(findAll(By.xpath("//h1[contains(text(),'No results found for the search criteria')]")).size()>0){
            if(!Values.contentEquals("no result")){
                Assert.fail("No results found for the search criteria");
            }
        }
        else
        {
            //To initiate Sort Validation
            if(Values.contentEquals("asc")){
                while(findAll(By.xpath("//th["+ColumnIndex+"]/a/img[@id='downarrow']")).size()<1) {
                    $("//tr/th[" + ColumnIndex + "]").click();
                }
                sortResults(getTableValues(ColumnIndex), Values);
            }
            else if(Values.contentEquals("dsc")){
                while(findAll(By.xpath("//th["+ColumnIndex+"]/a/img[@id='uparrow']")).size()<1) {
                $("//tr/th[" + ColumnIndex + "]").click();
                }
                sortResults(getTableValues(ColumnIndex), Values);
            }


            else {
               List<String> Expected = new ArrayList<>(Arrays.asList(Values.split("\\|")));
               List<String> Actual = new ArrayList<>(getTableValues(ColumnIndex));

                /*Collections.sort(Expected);
                Collections.sort(Actual);
                System.out.println("Actual: " + Actual + "\nExpected: " + Expected);*/

                //To Check if the result dates are present within the expected date range
                if (ColumnIndex.equals("7")) {
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
