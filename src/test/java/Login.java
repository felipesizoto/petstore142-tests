import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Login {
    
    private static final String BASE_URI = "https://petstore.swagger.io/v2";

    public static String login(String username, String password) {
        RestAssured.baseURI = BASE_URI; 

        return RestAssured 
            .given()
                .contentType(ContentType.JSON)
                .queryParam("username", username)
                .queryParam("password", password)
            .when()
                .get("/user/login")
            .then()
                .statusCode(200)
                .extract()
                .path("message");
                
    }
}
