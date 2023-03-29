package apiPacakge;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexNestedJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Sending complex json response as String because actual API not available to get this response
		JsonPath js = new JsonPath(Payload.complexJsonResponseString());
		
		int coursesCount = js.getInt("courses.size()");
		System.out.println(coursesCount);
		
		int purchaseAmt = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmt);
		
		String firstCourseTitle = js.getString("courses[0].title");
		System.out.println(firstCourseTitle);
		
		//Print All course titles and their respective Prices
		for(int i = 0; i < coursesCount; i++ ) {
			
			String courseTitle = js.getString("courses["+i+"].title");
			System.out.println(courseTitle);
			
			System.out.println(js.getInt("courses["+i+"].price"));  			
		}
		
		// Print no of copies sold by RPA Course
		for(int i = 0; i < coursesCount; i++ ) {
			
			if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
				
				System.out.println(js.getInt("courses["+i+"].copies"));
				break;
			}
		}
		
		// Verify if Sum of all Course prices matches with Purchase Amount
		int totAmtAll = 0;
		for(int i = 0; i < coursesCount; i++ ) {
			
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int totAmt = price * copies;
			totAmtAll = totAmtAll + totAmt;			
		}
		
		System.out.println("totAmtAll is " +totAmtAll);
		Assert.assertEquals(totAmtAll, js.getInt("dashboard.purchaseAmount"));
		System.out.println("Assertion passed");
	}

}
