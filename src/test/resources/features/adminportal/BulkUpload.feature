
Feature: Bulk Upload Subsidy Awards
  Scenario Outline: Successfully Bulk Upload Subsidy Awards
    Given I enter a valid emailID and password "<Credentials>" from "<DataSheet1>"
    When I click on SignIn
    Then I will be able to login successfully
    Then I am able to Bulk Upload Subsidy Awards from the spreadsheet "<DataSheet2>"
  Examples:
    |Credentials| DataSheet1| DataSheet2|
    |TD001| LoginData|BulkUpload|