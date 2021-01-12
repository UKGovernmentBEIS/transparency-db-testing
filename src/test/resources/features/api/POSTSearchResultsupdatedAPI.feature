Feature: POST request using /searchresults API

  Scenario Outline: Send a valid POST Request for valid search
    Given Payload is created with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |searchResults.endpoint| USTD_021| PublicSearch| 200 |
      |searchResults.endpoint| USTD_022| PublicSearch| 200 |
      |searchResults.endpoint| USTD_026| PublicSearch| 200 |
      |searchResults.endpoint| USTD_028| PublicSearch| 200 |


  Scenario Outline: Send an invalid POST Request for invalid search
    Given Payload is created with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |searchResults.endpoint| USTD_005| PublicSearch| 404 |
      |searchResults.endpoint| USTD_008| PublicSearch| 404 |
      |searchResults.endpoint| USTD_024| PublicSearch| 404 |
      |searchResults.endpoint| USTD_030| PublicSearch| 404 |
      |searchResults.endpoint| USTD_032| PublicSearch| 404 |
      |searchResults.endpoint| USTD_033| PublicSearch| 404 |

  @pagination
 Scenario Outline: Send a valid POST Request for valid search & validate pagination
    Given Payload is created with details from datasheet by passing "<TestData>" , "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |searchResults.endpoint| USTD_001| PublicSearch| 200 |
      |searchResults.endpoint| USTD_025| PublicSearch| 200 |