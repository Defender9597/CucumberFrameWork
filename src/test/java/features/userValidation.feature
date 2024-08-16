Feature: Validating Users data through GET API

Scenario: Verify if users data fetch by using user API
		Given Add user API with paramaeters
		When user calls "UserAPI" with GET http method
		Then API call success with 200 status code 
		And Received Get user data in response  
		
			