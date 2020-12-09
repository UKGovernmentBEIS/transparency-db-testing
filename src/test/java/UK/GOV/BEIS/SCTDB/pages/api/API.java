package UK.GOV.BEIS.SCTDB.pages.api;

import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.openapi4j.schema.validator.v3.SchemaValidator;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertEquals;

public class API {

    Response response;
    Reusable ReusableObj = new Reusable();
    private String BaseURI;
    private String BasePath;

    public void setEndPoint(String Endpoint){

        this.BaseURI = ReusableObj.getProperty("basepath.uri");
        this.BasePath = ReusableObj.getProperty(Endpoint);
    }

    public void getResponseForPOST(String Path) {
        String Payload ="{}";
        if(!Path.isEmpty()){
            Payload = ReusableObj.readPayload(Path);
        }
        response = SerenityRest.given()
                                        .baseUri(BaseURI)
                                        .basePath(BasePath)
                                .when()
                                        .body(Payload)
                                        .contentType(ContentType.JSON)
                                        .post()
                                .then()
                                        .extract()
                                        .response();
    }

    public void getResponseForGET(){
        response = SerenityRest.given()
                                        .baseUri(BaseURI)
                                        .basePath(BasePath)
                                .when()
                                        .get()
                                .then()
                                        .extract()
                                        .response();
    }


    public void validateStatusCode(int StatusCode){

        assertEquals("Error in Validating Status Code:",StatusCode, response.getStatusCode());

       /*
       response.then().assertThat().body(matchesJsonSchemaInClasspath("public_search_Swagger.json"));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode schemaNode = objectMapper.readTree(json);
        JsonNode contentNode = objectMapper.readTree(response);
        SchemaValidator schemaValidator = new SchemaValidator(null, schemaNode);

        JsonSchemaFactory factory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(
                        ValidationConfiguration.newBuilder()
                                .setDefaultVersion(SchemaVersion.DRAFTV3)
                                .freeze()).freeze();
        JsonSchemaValidator.settings = settings()
                .with().jsonSchemaFactory(factory)
                .and().with().checkedValidation(false);
        assertTrue(response.getBody().jsonPath().getMap("$").size()>0);*/

    }
}
