Feature: GET request using /awards API
  Scenario Outline: GET all awards
    Given an "<Endpoint>" to fetch all awards
    When I send a GET request
    Then I will be getting the successful <StatusCode> for GET Awards
    Examples:
      | Endpoint | StatusCode |
      | awards.endpoint |200 |