package Tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.*;
import utilities.ConfigurationReader;
import java.util.*;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class loginTest {

    @BeforeClass
    public void beforeClass(){
        baseURI = ConfigurationReader.get("ensek_url");
    }

    @Test
    public void loginTest01(){

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", ConfigurationReader.get("username"));
        credentials.put("password", ConfigurationReader.get("password"));

        given().contentType(ContentType.JSON)
                .body(credentials)
                .when().post("/login")
                .then().statusCode(200).log().all();

    }

}

