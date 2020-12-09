package UK.GOV.BEIS.SCTDB.runners.api;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = {"src/test/resources/features/api/GETAwardsAPI.feature"}
        ,glue= "UK/GOV/BEIS/SCTDB/steps/api")
public class Awards_APIRunner {
}

