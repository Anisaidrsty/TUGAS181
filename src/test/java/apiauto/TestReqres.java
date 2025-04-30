package apiauto;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;

public class TestReqres {

    @Test
    public void testGetListUsers() {

        File jsonSchema =new File("src/test/resources/jsonSchema/getListUsersSchema.json");

        RestAssured
                .given().when()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(2))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
    }

    @Test
    public void testPostCreateUser() {
        String valueName = "Anisa";
        String valueJob = "QA";

        JSONObject bodyObj = new JSONObject();

        bodyObj.put("name", valueName);
        bodyObj.put("job", valueJob);

        RestAssured.given()
                .header("Content-type", "application/json")
                .header("Accept", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/users").then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("name", Matchers.equalTo(valueName));

    }
}
