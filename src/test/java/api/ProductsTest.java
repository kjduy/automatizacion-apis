package api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductsTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Test
    @DisplayName("Validate specific product with ID 10")
    public void validateSpecificProduct() {
        given()
            .when()
                .get("/products")
            .then()
                .statusCode(200)
                .body("find { it.id == 10 }.id", equalTo(10))
                .body("find { it.id == 10 }.category", equalTo("electronics"))
                .body("find { it.id == 10 }.title", equalTo("Silicon Power 256GB SSD 3D NAND A55 SLC Cache performance Boost SATA III 2.5"))
                .body("find { it.id == 10 }.rating.rate", equalTo(2.9f));
    }

    @Test
    @DisplayName("Validate total number of electronics products")
    public void validateElectronicsProducts() {
        given()
            .when()
                .get("/products/category/electronics")
            .then()
                .statusCode(200)
                .body("size()", equalTo(6))
                .body("find { it.id == 10 }.id", equalTo(10))
                .body("every { it.category == 'electronics' }", equalTo(true));
    }

    @Test
    @DisplayName("Validate limited electronics products")
    public void validateLimitedElectronicsProducts() {
        given()
            .queryParam("limit", 3)
            .when()
                .get("/products/category/electronics")
            .then()
                .statusCode(200)
                .body("size()", equalTo(3))
                .body("every { it.category == 'electronics' }", equalTo(true));
    }
}
