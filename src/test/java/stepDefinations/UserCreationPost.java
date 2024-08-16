package stepDefinations;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.types.StepDefinition;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojo.UserDetail;
import pojo.UserDetailsResponse;

public class UserCreationPost {
	 RequestSpecification req;
	 RequestSpecification res;
	 ResponseSpecification resspec;
	
	 Response response;
	
	 private static final Logger logger = LogManager.getLogger(UserCreationPost.class);
	 
	@Given("Add a user creation payload")
	public void add_a_user_creation_payload() {
		
		logger.info("Setting up API endpoint");
		
		RestAssured.baseURI ="https://reqres.in";
		UserDetail ud = new UserDetail();
		ud.setName("morpheus");
		ud.setJob("leader");
	

		req = new RequestSpecBuilder().setBaseUri("https://reqres.in").addHeader("Content-Type", "application/json").build();

		resspec = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(JSON).build();
		res = given().spec(req).body(ud);

	}

	@When("Users call createUser APO using POST http request")
	public void users_call_create_user_apo_using_post_http_request() {
		
		  logger.info("Sending POST request");
		
		response = res.when().post("/api/users").then().spec(resspec)
				.assertThat().statusCode(201).extract().response();
	}

	@Then("API call got success with status code {int}")
	public void api_call_got_success_with_status_code(Integer statusCode) {
		// Write code here that turns the phrase above into concrete actions

		response.then().statusCode(statusCode);
	}

	@Then("User created with response")
	public void user_created_with_response() throws JsonMappingException, JsonProcessingException {
		
		logger.info("Verifying response");
		ObjectMapper om = new ObjectMapper();
		
		UserDetailsResponse udr = om.readValue(response.asString(), UserDetailsResponse.class);

		System.out.println("User ID :" + udr.getId());
		System.out.println("User Name :" + udr.getName());
		System.out.println("User Job :" + udr.getJob());
		System.out.println("UserCreatedDate :" + udr.getCreatedAt());

	}
}
