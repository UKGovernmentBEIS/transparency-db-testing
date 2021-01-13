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
        map.put("userName", "SYSTEM");
        map.put("password", "password123");
        map.put("role", "Granting Authority Administrator");
        map.put("grantingAuthorityGroupId", "123");
        map.put("grantingAuthorityGroupName", "HMRC");
        //String value = "{\"userName\":\"SYSTEM\",\"password\":\"password123\",\"role\":\"BEIS Administrator\",\"grantingAuthorityGroupId\":\"123\",\"grantingAuthorityGroupName\":\"HMRC\"}";
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://access-management-service.azurewebsites.net")
                .setContentType(ContentType.JSON).build();
        RequestSpecification resp = given().spec(req).header("userPrinciple", map);
        ResponseSpecification resspec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200).build();
        Response respons = resp.when().get("/accessmanagement/gaadmin").
                then().spec(resspec).extract().response();
        String responseString = respons.asString();
        System.out.println(respons);
        System.out.println(responseString);
    }
}

