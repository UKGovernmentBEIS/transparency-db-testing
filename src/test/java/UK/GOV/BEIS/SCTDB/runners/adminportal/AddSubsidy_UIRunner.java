package UK.GOV.BEIS.SCTDB.runners.adminportal;

import UK.GOV.BEIS.SCTDB.utilities.BrowserStackSerenityTest;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/adminportal/AddSubsidy.feature"}
        ,glue= "UK/GOV/BEIS/SCTDB/steps/adminportal")

public class AddSubsidy_UIRunner {//extends BrowserStackSerenityTest {

}
