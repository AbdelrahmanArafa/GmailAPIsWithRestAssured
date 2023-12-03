package Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class InvalidScenarios {
    private static final String BASE_URI = "https://www.googleapis.com/gmail/v1/users/";
    private static final String ACCESS_TOKEN =
            "\n" +
                    "ya29.a0AfB_byAuU9xjpAdVhIoHcUkvnlWJE-4n4WtB1L3GSwczi_Zx512x4Dgnyhv7frP9ij89Aj3wJPkr652YJmyoFLBSWhvYEozbeWxOMmuSM1_0t3qgoES_z5o1_bVbMgtX6qYt0EzQsigtsv5SER_o6Okw3aCDbkNeDacaCgYKARwSARMSFQHGX2MiIqZUS7O5Op8oDl4lqL6cWA0170";

    @Test
    public void ListMessagesWithInvalidAccessToken() {
        Response response = RestAssured.given()
                .baseUri(BASE_URI + "me/messages")
                .header("Authorization", "Bearer " + "invalid_access_token")
                .when()
                .get();
        response.then().log().all().assertThat().statusCode(401); // Expected: Unauthorized
    }

    @Test
    public void GetMessageInvalidID() {
        Response response = RestAssured.given()
                .baseUri(BASE_URI + "me/messages/11")
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .when()
                .get();
        response.then().log().all().assertThat().statusCode(404); // Expected: Not Found
    }

    @Test
    public void SendMessageWithInvalidRequestFormat() {
        String invalidBody = "{ \"invalid_json\" }";
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body(invalidBody)
                .post(BASE_URI + "me/messages/send");
        response.then().log().all().assertThat().statusCode(400); // Expected: Bad Request
    }

    @Test
    public void SendMessageWithMaliciousContent() {
        String maliciousBody = "{\n" +
                "    \"raw\" : \"Malicious content here\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body(maliciousBody)
                .post(BASE_URI + "me/messages/send");
        response.then().log().all().assertThat().statusCode(400); // Expected: Bad Request or other appropriate status code
    }

    @Test
    public void SendEmptyMessage() {
        String emptyBody = "{\n" +
                "    \"raw\" : \"\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body(emptyBody)
                .post(BASE_URI + "me/messages/send");
        response.then().log().all().assertThat().statusCode(400); // Expected: Bad Request or other appropriate status code
    }
}
