package UK.GOV.BEIS.SCTDB.runners.searchportal;

import UK.GOV.BEIS.SCTDB.utilities.BrowserStackSerenityTest;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/searchportal/UserSearch.feature"}
        ,glue= "UK/GOV/BEIS/SCTDB/steps/searchportal")

public class UserSearch_UIRunner {//extends BrowserStackSerenityTest {

}
