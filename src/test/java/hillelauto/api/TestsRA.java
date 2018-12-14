package hillelauto.api;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestsRA {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://37.59.228.229:3000/API/users/";
    }

    @DataProvider
    public Object[][] newUserData() {
        return new Object[][] { { "{\"name\":\"Test\",\"phone\":\"1111\",\"role\":\"Student\"}" } };
    }

    @Test(description = "Fourth requirement - creating users", dataProvider = "newUserData")
    void createUser(String userData) {
        RequestSpecification req = RestAssured.given().header("Content-Type", "application/json");
        req.body(userData);

        Response res = req.request("POST", "/");
        // res.peek();
        res.then().header("Content-Type", "application/json; charset=utf-8").body("id", Is.isA(Integer.class));

        String newUser = res.getBody().asString();
        req.request("GET", "/").then().body(CoreMatchers.containsString(newUser));
    }

}
