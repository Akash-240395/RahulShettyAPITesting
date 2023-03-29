package apiPacakge;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.Payload;
import files.ReusableMethods;

public class BasicAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Adding place API

		// Works on the principle of
		// given - all input details
		// when - sending the response (resource and http req)
		// then - validating the response

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123")
				.body(Payload.addPlaceBody())
				.when().post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		// For parsing json
		JsonPath js = ReusableMethods.rawToJson(response);
		String placeID = js.getString("place_id");	
		
		System.out.println(placeID);
		
		// update place
		given().log().all().queryParam("key", "qaclick123")
		.body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\"70 Summer walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200);
		
		// Get place
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).body("address", equalTo("70 Summer walk, USA"));
		
		
		
		
		
		
		
		
		
		
		
	}

}
