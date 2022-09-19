package Tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.*;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class verifyOrderGivenInFeb {

    @BeforeClass
    public void beforeClass() {
        baseURI = ConfigurationReader.get("ensek_url");
    }

    @Test
    public void chekingOrderDate() {

        Response response = given().accept(ContentType.JSON)
                .get("orders");

        assertEquals(response.statusCode(), 200);

        List<Object> order = response.body().as(List.class);

        List<Map<String, Object>> orderList = new ArrayList<>();

        Iterator iterator = order.iterator();

        while (iterator.hasNext()) {

            orderList.add((Map<String, Object>) iterator.next());

        }
        List<String> allOrderDates = new ArrayList<>();

        for (Map<String, Object> eachOrder : orderList) {

            String[] time = ((String) eachOrder.get("time")).split(" ");

            allOrderDates.add(time[2]);
        }

        //System.out.println(allOrderDates);
        int count = 0;
        for (String eachOrderDate : allOrderDates) {
            if (eachOrderDate.equals("Feb")) {
                count++;
            }
        }
        assertEquals(count, 2);


    }

}