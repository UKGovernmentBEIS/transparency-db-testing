Feature: POST request using /searchresults API

  Scenario Outline: Send a valid POST Request for valid search
    Given Payload is created with details from datasheet by passing values "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response and write beneficiary names to datasheet
    Examples:
      |Endpoint | TestData | DataSheet |
      |searchResults.endpoint| USTD_001| PublicSearch|
      #|searchResults.endpoint| USTD_002| PublicSearch| 200 |
      #|searchResults.endpoint| USTD_021| PublicSearch| 200 |
      #|searchResults.endpoint| USTD_022| PublicSearch| 200 |
      #|searchResults.endpoint| USTD_026| PublicSearch| 200 |


