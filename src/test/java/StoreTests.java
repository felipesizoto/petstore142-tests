import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.hamcrest.Matchers.is;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreTests {
    
    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private final Gson gson = new Gson();   

    private static final long ORDER_ID = 5001; 
    private static final long PET_ID = 101; 

    @Test
    @Order(1)
    void testCreateStoreOrder(){
        RestAssured.baseURI = BASE_URI;

        Store order = new Store ();
        order.id = ORDER_ID;
        order.petId = PET_ID;
        order.quantity = 2;
        order.shipDate = "2025-09-20T19:36:18.480Z";
        order.status = "placed";
        order.complete = false;

        String orderJson = gson.toJson(order);

        RestAssured
            .given()    
                .contentType(ContentType.JSON)
                .body(orderJson)
                .log().all()
            .when()
                .post("/store/order")
            .then()
                .log().all()
                .statusCode(200)
                .body("id", is((int)ORDER_ID));
    }

    @Test
    @Order(2)
    void testGetStoreOrder(){
        RestAssured.baseURI = BASE_URI; 

        RestAssured
        .given()
            .contentType(ContentType.JSON)
            .log().all()
        .when()
            .get("/store/order/" + ORDER_ID)
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is((int) ORDER_ID))
            .body("petId", is((int) PET_ID))
            .body("quantity", is(2))
            .body("status", is ("placed"))
            .body("complete", is(false)); 

    }

    @Test
    @Order(3)
    void testDeleteStoreOrder() {
        RestAssured.baseURI = BASE_URI;

        RestAssured
        .given()
            .contentType(ContentType.JSON)
            .log().all()
        .when()
            .delete("/store/order/" + ORDER_ID)
        .then()
            .log().all()
            .statusCode(200)
            .body("message", is(String.valueOf(ORDER_ID)));

    } 

    @ParameterizedTest
    @Order(4)
    @CsvFileSource(resources = "/csv/storeMassa.csv", numLinesToSkip = 1)
     void testCreateStoreOrderDDT(int id, int petId, int quantity, String shipDate, String status, boolean complete) {
        RestAssured.baseURI = BASE_URI;
        
        Store order = new Store();
        order.id = id;
        order.petId = petId;
        order.quantity = quantity;
        order.shipDate = shipDate;
        order.status = status;
        order.complete = complete;

        String orderJson = gson.toJson(order);

        RestAssured 
            .given()
                .contentType(ContentType.JSON)
                .body(orderJson)
                .log().all()
            .when()
                .post("/store/order")
            .then()
                .statusCode(200)
                .body("id", is(id))
                .body("petId", is(petId))
                .body("quantity", is(quantity))
                .body("status", is(status))
                .body("complete", is(complete)); 

    } 

     @AfterEach
    void shortWait() {
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
