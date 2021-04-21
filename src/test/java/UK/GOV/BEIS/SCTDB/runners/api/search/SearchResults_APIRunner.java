package UK.GOV.BEIS.SCTDB.runners.api.search;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = {"src/test/resources/features/api/search/POSTSearchResultsupdatedAPI.feature"}
        ,glue= "UK/GOV/BEIS/SCTDB/steps/api")

public class SearchResults_APIRunner {
}