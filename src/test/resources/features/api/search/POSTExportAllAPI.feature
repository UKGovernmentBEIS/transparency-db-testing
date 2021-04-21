#Feature: POST request using /searchResults/exportAll API

  Scenario Outline: Send a valid POST Request for export all search results
    Given Payload is created with details from datasheet by passing "<TestData>" and "<DataSheet>"
    When I call "<Endpoint>" API with "Post" http request
   # Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |exportall.endpoint| USTD_001| PublicSearch| 200 |