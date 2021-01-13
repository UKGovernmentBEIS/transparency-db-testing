package UK.GOV.BEIS.SCTDB.runners.api;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = {"src/test/resources/features/api/POSTSingleSubsidyAwardsAPI.feature"}
        ,glue= {"UK/GOV/BEIS/SCTDB/steps/api"},tags="@valid")

public class SingleSubsidyAward_APIRunner {
}