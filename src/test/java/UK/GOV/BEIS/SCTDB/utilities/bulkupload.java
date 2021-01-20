package UK.GOV.BEIS.SCTDB.utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.openqa.selenium.json.Json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class bulkupload {
    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", "1");
        map.put("recordsPerPage", "10000");
        map.put("status", "Published");
        map.put("searchName", "Aid for economic development - Growth Deal");
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://integ-transparency-db-access-management-service.azurewebsites.net")
                .setContentType(ContentType.JSON).build();
        RequestSpecification resp = given().spec(req).queryParams(map);
        ResponseSpecification resspec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200).build();
        Response respons = resp.when().get("/accessmanagement/searchresults").
                then().spec(resspec).extract().response();
        String responseString = respons.asString();
        System.out.println(respons);
        System.out.println(responseString);
    }
}

