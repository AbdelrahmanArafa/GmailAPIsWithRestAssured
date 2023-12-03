package Test;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.hamcrest.Matchers.*;



public class ValidScenarios {

    private static final String BASE_URI = "https://www.googleapis.com/gmail/v1/users/";
    private static final String ACCESS_TOKEN =
            "ya29.a0AfB_byAuU9xjpAdVhIoHcUkvnlWJE-4n4WtB1L3GSwczi_Zx512x4Dgnyhv7frP9ij89Aj3wJPkr652YJmyoFLBSWhvYEozbeWxOMmuSM1_0t3qgoES_z5o1_bVbMgtX6qYt0EzQsigtsv5SER_o6Okw3aCDbkNeDacaCgYKARwSARMSFQHGX2MiIqZUS7O5Op8oDl4lqL6cWA0170";

    @Test
    public void ListAllMessages() {
        Response response = RestAssured.given()
                .baseUri(BASE_URI + "me/messages")
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
        .when()
                .get();
        response.then()
                .log().all().assertThat().statusCode(200)
                .body(not(empty()));
    }

    @Test
    public void ListSingleMessageWithID() {
        Response response = RestAssured.given()
                .baseUri(BASE_URI + "me/messages/18bc4fdb1754d97a")
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
        .when()
                .get();
        response.then()
                .log().all().assertThat().statusCode(200)
                .body(not(empty()));
    }

    @Test
    public void SendMessage() {
        String body = "{\n" +
                "    \"raw\" : \"RnJvbTogbTdtb2QuM3JhZmFAZ21haWwuY29tClRvOiBBYmRlbHJhaG1hbkFyYWZhT3NtYW5AZ21haWwuY29tClN1YmplY3Q6IFNheWluZyBIZWxsbwoKVGhpcyBpcyBhIG1lc3NhZ2UganVzdCB0byBzYXkgaGVsbG8uClNvLCAiSGVsbG8iLg==\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Content-Type", "application/json")
                .body(body)
                .post(BASE_URI + "me/messages/send");
        response.then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void MoveMessageToTrash() {
        Response response = RestAssured.given()
                .baseUri(BASE_URI + "me/messages/18bc4fdb1754d97a/trash")
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
        .when()
                .post();
        response.then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void UntrashMessage() {
        Response response = RestAssured.given()
                .baseUri(BASE_URI + "me/messages/18bc4fdb1754d97a/untrash")
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
        .when()
                .post();
        response.then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void DeleteMessage() {
        Response response = RestAssured.given()
                .baseUri(BASE_URI + "me/messages/18bc4eb03ce9f751")
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .when()
                .delete();
        response.then().log().all().assertThat().statusCode(204);
    }

}

