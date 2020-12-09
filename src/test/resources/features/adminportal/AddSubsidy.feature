Feature: Add Subsidy Values
  Scenario Outline: Successfully Add Subsidy Values from Test Data sheet
    Given I enter a valid emailID and password "<Credentials>" from "<DataSheet1>"
    When I click on SignIn
    Then I will be able to login successfully
    Given I enter a valid "<Values>" from "<DataSheet2>"
  Examples:
    |Credentials| DataSheet1|Values| DataSheet2|
    |TD001| LoginData|ASTD_002| AddSubsidy|