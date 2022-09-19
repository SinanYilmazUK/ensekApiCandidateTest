package Tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.*;
import utilities.ConfigurationReader;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;
import static  org.hamcrest.Matchers.*;

public class verifyRemainingAmount {

    @BeforeClass
    public void beforeClass(){
        baseURI = ConfigurationReader.get("ensek_url");
    }

    @Test
    public void verifyRemaining(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",3)
                .and().pathParam("quantity",1)
                .when().put("/buy/{id}/{quantity}");

        assertEquals(response.statusCode(),200);

        String message = response.path("message");

        String[] arr = message.split(" ");

        Integer remainingAmount = Integer.parseInt(arr[12]);

        /**
         Compare the amount which is at energy endpoint with the amount taken from response body by using buy endpoint.
         In this comparision, it is used 'hamcrest.Matchers' method.
         */
                given().accept(ContentType.JSON).
                when().get("/energy")
                .then().assertThat().statusCode(200)
                .and().contentType(equalTo("application/json"))
                .and().header("Content-Length",is("361"))
                .and().header("Date",notNullValue())
                .and().assertThat().body("electric.quantity_of_units",equalTo(remainingAmount),  // ** remainingAmount comes from buy endpoint
                        "electric.price_per_unit",equalTo(0.47F));

    }

}

