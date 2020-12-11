package UK.GOV.BEIS.SCTDB.runners.searchportal;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/searchportal/AdvancedSearch.feature"}
        ,glue= "UK/GOV/BEIS/SCTDB/steps/searchportal")

public class AdvancedSearch_UIRunner {

}
