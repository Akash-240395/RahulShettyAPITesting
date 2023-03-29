package apiPacakge;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class LibraryAPI {
	
	@Test(dataProvider = "bookData")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().body(Payload.addBookBody(isbn, aisle))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String ID = js.getString("ID");
		System.out.println(ID);	
		
		// Delete book
		given().body(Payload.deleteBookBody(ID))
		.when().post("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200);
	}
	
	@DataProvider
	public Object[][] bookData() {
		
		return new Object[][] {{"ash","123"}, {"ash", "456"}, {"ash","789"}};
	}	
	//array=collection of elements
	//multidimensional array= collection of arrays
}
