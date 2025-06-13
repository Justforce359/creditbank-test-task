import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditBankApiTest {

    @Test
    @DisplayName("API возвращает клиента с фамилией Иванов")
    void shouldReturnClientDataForValidQuery() {
        String baseUrl = "http://37.203.243.26:5000";
        Response response = RestAssured
                .given()
                .queryParam("q", "Иванов")
                .when()
                .get(baseUrl + "/api/search")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        var names = json.getList("data.name", String.class);

        System.out.println("Найденные имена: " + names);

        boolean found = names.stream().anyMatch(name -> name.contains("Иванов"));
        assertTrue(found, "Ответ не содержит фамилию Иванов");
    }
}
