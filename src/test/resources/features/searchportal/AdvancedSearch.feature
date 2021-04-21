Feature: Advanced Search


  Scenario Outline: Public User Search: Record of Selections
    Given I have test data "<TestData>" from "<DataSheet>"
    Given I am on the Public User Search Portal
    And I have completed my entries into the search fields
    When I want to return to check my previous inputs
    Then I want to be able to see my previous entries so I can keep track of what I have entered.

  Examples:
    |TestData| DataSheet|
    |USTD_032| PublicSearch|
    |USTD_033| PublicSearch|