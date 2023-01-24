import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.LocalDate;


import static io.restassured.RestAssured.given;

public class ApiTests {

    //Напомнить про Postman - перенести генераторы данных в запрос

    public final String BASE_URI = "https://reqres.in";
    @Test
    public void getListUser1() {
        given()
                .when()
                .log().all()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all();
    }

    @Test
    public void getListUser2() {
        Response response = given()
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .get("/api/users?page=2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200, "The actual status code does not match 200");
        //показать как найти jsonpath онлайн https://www.site24x7.com/tools/jsonpath-finder-validator.html
        Assert.assertEquals(response.body().jsonPath().getInt("data[0].id"), 7, "The actual id does not match the expected id");
        Assert.assertEquals(response.body().jsonPath().getString("data[0].email"), "michael.lawson@reqres.in", "The actual email does not match the expected email");
    }

    @Test
    public void getListUser3() {
        given()
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .get("/api/users?page=2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("data[0].id", Matchers.equalTo(7))
                .body("data[0].email", Matchers.equalTo("michael.lawson@reqres.in"))
                // показать с regex
                .body("data[0].first_name", Matchers.matchesRegex("\\w+"));
    }

    @Test
    public void createUser1() {
        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .post("/api/users")
                .then()
                .log().all()
                .assertThat()
                .body("name", Matchers.equalTo("morpheus"));
    }

    @Test
    public void createUser2() {
        CreateUser user = new CreateUser("morpheus", "leader");
        given()
                .header("Content-type", "application/json")
                .body(user.toString())
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .post(BASE_URI + "/api/users")
                .then()
                .log().all()
                .assertThat()
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("leader"))
                .body("createdAt", Matchers.containsString(LocalDate.now().toString()));
    }

    @Test
    public void createUser3() {
        CreateUserJson user = new CreateUserJson("morpheus", "leader");
        given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .post(BASE_URI + "/api/users")
                .then()
                .log().all()
                .assertThat()
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("leader"))
                .body("createdAt", Matchers.containsString(LocalDate.now().toString()));
    }




}
