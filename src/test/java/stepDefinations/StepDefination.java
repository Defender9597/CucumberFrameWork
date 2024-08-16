package stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.types.StepDefinition;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.Data;
import pojo.GetUsers;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StepDefination {

	private static final Logger logger = LogManager.getLogger(StepDefinition.class);

	 	RequestSpecification reqSpec;
	    Response response;
	
	@Given("Add user API with paramaeters")
	public void add_user_api_with_paramaeters() {
		logger.info("Setting up API endpoint");;
			reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in").addHeader("Accept", "application/json").build();
				
				
	}
	@When("user calls {string} with GET http method")
	public void user_calls_with_get_http_method(String string) {
	    
		logger.info("Sending GET request");
		 response = RestAssured.given().spec(reqSpec).queryParam("page", 2).when().get("/api/users");  
	}
	@Then("API call success with {int} status code")
	public void api_call_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	   
		  response.then().assertThat().statusCode(200);
	}
	@Then("Received Get user data in response")
	public void is_in_response_body_is() throws JsonMappingException, JsonProcessingException {
	   
		logger.info("Verifying response");
		ObjectMapper om = new ObjectMapper();
		GetUsers gu = om.readValue(response.asString(), GetUsers.class);
		
		System.out.println("Getting the page number :" +gu.getPage());
		System.out.println("Getting the Per page number :" +gu.getPer_page());
		System.out.println("Getting the total number :" +gu.getTotal());
		System.out.println("Getting the Totalpage number :" +gu.getTotal_pages());
		
		System.out.println("Getting URL :" +gu.getSupport().getUrl());
		System.out.println("Getting Text :" +gu.getSupport().getText());
		List<Data> getData = gu.getData();
		for (int i=0; i<getData.size();i++)
		{
			System.out.println("GettingID from Data List :"+getData.get(i).getId());
			System.out.println("Getting Email from Data List :"+getData.get(i).getEmail());
			System.out.println("Getting Firstname from Data List :"+getData.get(i).getFirst_name());
			System.out.println("Getting LastName from Data List :"+getData.get(i).getLast_name());
			System.out.println("Getting avatar from Data List :"+getData.get(i).getAvatar());
			System.out.println();
	}

	}
}
