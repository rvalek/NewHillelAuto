package hillelauto.api;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestsRA {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://37.59.228.229:3000/API/users/";
    }

    @DataProvider
    public Object[][] newUserData() {
        return new Object[][] { { "{\"name\":\"Test Student\",\"phone\":\"1111\"}" },
                { "{\"name\":\"Test Admin\",\"phone\":\"1111\",\"role\":\"Administrator\"}" },
                { "{\"name\":\"Test Support\",\"phone\":\"1111\",\"role\":\"Support\"}" } };
    }

    @Test(description = "Fourth requirement - creating users", dataProvider = "newUserData")
    void createUser(String userData, String userRole) {
        Response res = RestAssured.given().header("Content-Type", "application/json").body(userData).post("POST", "/");

        res.peek().then().statusCode(200).header("Content-Type", "application/json; charset=utf-8").body("id", Is.isA(Integer.class));

        RestAssured.given().header("Content-Type", "application/json").request("GET", "/").then()
                .body(CoreMatchers.containsString(res.getBody().asString()));

        // RequestSpecification req = RestAssured.given();
        // req.header("Content-Type", "application/json");
        // req.body(userData);

        // Response res = req.request("POST", "/");
        // res.peek();
        // res.then().header("Content-Type", "application/json;
        // charset=utf-8").body("id", Is.isA(Integer.class));

        // String newUser = res.getBody().asString();
        // req.request("GET", "/").then().body(CoreMatchers.containsString(newUser));
    }

}
