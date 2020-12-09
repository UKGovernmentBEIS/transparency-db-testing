package UK.GOV.BEIS.SCTDB.runners;

import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class TestRunner {

   @Test
    public void test(){
        //System.out.println(new Reusable().readExcelToJSON("./src/test/resources/data/sample.xlsx"));

//        for(Object object: new Reusable().readExcelData("./src/test/resources/data/sample.xlsx","AddSubsidy","ASTD_003")) {
//        System.out.println(object);}

       HashMap<String,String> test = new Reusable().readExcelDataNew("./src/test/resources/data/sample.xlsx","PublicSearch","USTD_002");
       test.forEach((key, value) -> System.out.println(key + " | " + value));




    }
}

