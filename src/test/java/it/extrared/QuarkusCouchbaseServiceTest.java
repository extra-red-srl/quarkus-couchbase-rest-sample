package it.extrared;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class QuarkusCouchbaseServiceTest {

    //@Test
    public void testHelloEndpoint() {
        given()
          .when().get("/quarkus/sample/getFile/123")
          .then()
             .statusCode(200)
             .body(is("{\"testSenzaCamel\":\"Yo\"}"));
    }

}