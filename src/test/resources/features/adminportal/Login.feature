Feature: User provides details for a successful login
  Scenario Outline: Valid user credentials provided
     Given I enter a valid emailID and password "<TestData>" from "<DataSheet>"
     When I click on SignIn
     Then I will be able to login successfully
     Examples:
        |TestData| DataSheet|
        |TD001| LoginData|