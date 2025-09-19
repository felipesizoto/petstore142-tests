import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType; 

public class UserTests {

    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private final Gson gson = new Gson(); 

    private static final int USER_ID = 397458401;
    private static final String USERNAME = "felipeAugusto"; 

    
    @Order(1)
    void testCreatUser(){
        RestAssured.baseURI = BASE_URI; 

        User user = new User();
            user.id = USER_ID;
            user.username = USERNAME;
            user.firstName = "Felipe"; 
            user.lastName = "Augusto"; 
            user.email = "fsizoto@gmail.com"; 
            user.password = "123456";
            user.phone = "11999998888";
            user.userStatus = 1; 
        
        String userJson = gson.toJson(user);

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body(userJson)
                .log().all()
            .when()
                .post("/user")
            .then()
                .log().all()
                .statusCode(200)
                .body("message", notNullValue());    
    }

    @Test
    @Order(2)
    void testGetUser(){
        RestAssured.baseURI = BASE_URI; 

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .log().all()
            .when()
                .get("/user" + USERNAME)
            .then()
                .log().all()
                .statusCode(200)
                .body("username", is(USERNAME))
                .body("firstName", is("Felipe"))
                .body("lastName", is("Augusto"))
                .body("email", is("fsizoto@gmail.com"))
                .body("password", is("123456"))
                .body("phone", is("11999998888"))
                .body("userStatus", is(1));
    }

    @Test
    @Order(3)
    void testUpdateUser(){
        RestAssured.baseURI = BASE_URI; 

        User updateUser = new User();
        updateUser.id = USER_ID;
        updateUser.username = USERNAME;
        updateUser.firstName = "Matias";
        updateUser.lastName = "Oliveira"; 
        updateUser.email = "matias.oliveira@test.com"; 
        updateUser.password = "456123"; 
        updateUser.phone = "11988888888";
        updateUser.userStatus = 1; 

        String userJson = gson.toJson(updateUser);

        RestAssured
            .given()
    }
}
