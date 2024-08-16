Feature: Validate user get created using POST API

Scenario: Verify if user gets created using createUser API
			Given Add a user creation payload
			When Users call createUser APO using POST http request
			Then API call got success with status code 201 
			And User created with response 