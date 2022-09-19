package Tests;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;
import utilities.ConfigurationReader;

import java.util.*;

public class buyingEnergy {

    @BeforeClass
    public void beforeClass(){
        baseURI = ConfigurationReader.get("ensek_url");
    }
    /**
     Inside this test, first thing needed to be done is getting the created ID from API. Normally the response contains only one message.
     That why The ID number should be came off from whole message. The "split()" method have been used to make message seperated by each space and take
     Id individually by using index number.
     */
    @Test
    public void buyingEnergyfromList(){

        Response responseBUY = given().accept(ContentType.JSON)
                .and().pathParam("id", 1)
                .and().pathParam("quantity", 10)
                .when().put("buy/{id}/{quantity}");

        assertEquals(responseBUY.statusCode(),200);

        JsonPath jsonPath = responseBUY.jsonPath();

        String[] message = jsonPath.getString("message").split(" ");

        String LastestSalesID = message[message.length-1].substring(0,(message[message.length-1]).length()-1);

        //System.out.println(LastestSalesID);

        Response responseOrder = given().accept(ContentType.JSON)
                                    .get("orders");

        assertEquals(responseOrder.statusCode(),200);

        List<Object> order = responseOrder.body().as(List.class);

        List<Map<String, Object>> orderList = new ArrayList<>();

        Iterator iterator = order.iterator();

        while(iterator.hasNext()){

            orderList.add((Map<String,Object>)iterator.next());

        }
        List<String> allOrderIDs = new ArrayList<>();

        for (Map<String, Object> eachOrder : orderList) {

            allOrderIDs.add((String) eachOrder.get("id"));

        }
        //System.out.println(allOrderIDs);
        assertTrue(allOrderIDs.contains(LastestSalesID));


    }


}
