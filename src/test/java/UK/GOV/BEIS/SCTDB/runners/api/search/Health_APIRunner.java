package UK.GOV.BEIS.SCTDB.runners.api.search;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = {"src/test/resources/features/api/GETHealthAPI.feature"}
        ,glue= "UK/GOV/BEIS/SCTDB/steps/api")
public class Health_APIRunner {
}

