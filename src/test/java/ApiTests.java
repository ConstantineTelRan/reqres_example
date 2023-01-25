import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.request.CreateUserJson;
import model.response.CreateUserResp;
import model.response.GetListResp;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.LocalDate;


public class ApiTests {

    public final String BASE_URI = "https://reqres.in";
    // Пример выполнения get запроса без проверок
    @Test
    public void getListUser1() {
        RestAssured.given()
                .when()
                .log().all()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all();
    }

    // Пример выполнения get запроса с проверкой полей с помощью TestNg
    @Test
    public void getListUser2() {
        Response response = RestAssured.given()
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

    // Пример выполнения get запроса с проверкой полей с помощью Rest Assured
    @Test
    public void getListUser3() {
        RestAssured.given()
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

        RestAssured.given()
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
        RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .post("/api/users")
                .then()
                .log().all()
                .assertThat()
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("leader"))
                .body("createdAt", Matchers.containsString(LocalDate.now().toString()));
    }


    @Test
    public void createUser_5() {
        CreateUser user = new CreateUser("morpheus", "leader");
        CreateUserResp createUserResp = RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .post(BASE_URI + "/api/users")
                .then()
                .log().all()
                .extract().as(CreateUserResp.class);
        Assert.assertEquals(createUserResp.name, "morpheus");
        Assert.assertTrue(createUserResp.createdAt.contains(LocalDate.now().toString()));

    }

    @Test
    public void createUser_6() {
        CreateUserJson user = new CreateUserJson("owoeoe");
        CreateUserResp createUserResp = RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .post(BASE_URI + "/api/users")
                .then()
                .log().all()
                .extract().as(CreateUserResp.class);
        Assert.assertEquals(createUserResp.name, "morpheus");
        Assert.assertTrue(createUserResp.createdAt.contains(LocalDate.now().toString()));

    }

//    https://json2csharp.com/code-converters/json-to-pojo

    @Test
    public void getListUserRespClass() {
        GetListResp getListResp = RestAssured.given()
                .when()
                .baseUri(BASE_URI)
                .log().all()
                .get("/api/users?page=2")
                .then()
                .log().all()
                .extract().as(GetListResp.class);
        Assert.assertEquals(getListResp.data.get(5).first_name, "Rachel");

    }

}
