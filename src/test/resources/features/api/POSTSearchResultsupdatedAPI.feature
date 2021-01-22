Feature: POST request using /searchresults API

  Scenario Outline: Send a valid POST Request for valid search
    Given Payload is created with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet |
      |searchResults.endpoint| TD_001| PublicSearch|
      |searchResults.endpoint| TD_002| PublicSearch|
      |searchResults.endpoint| TD_003| PublicSearch|
      |searchResults.endpoint| TD_004| PublicSearch|
      |searchResults.endpoint| TD_005| PublicSearch|
      |searchResults.endpoint| TD_006| PublicSearch|
      |searchResults.endpoint| TD_007| PublicSearch|
      |searchResults.endpoint| TD_008| PublicSearch|
      |searchResults.endpoint| TD_009| PublicSearch|
      |searchResults.endpoint| TD_010| PublicSearch|
      |searchResults.endpoint| TD_011| PublicSearch|
      |searchResults.endpoint| TD_012| PublicSearch|
      |searchResults.endpoint| TD_013| PublicSearch|
      |searchResults.endpoint| TD_014| PublicSearch|
      |searchResults.endpoint| TD_015| PublicSearch|
      |searchResults.endpoint| TD_016| PublicSearch|
      |searchResults.endpoint| TD_017| PublicSearch|
      |searchResults.endpoint| TD_018| PublicSearch|
      |searchResults.endpoint| TD_019| PublicSearch|
      |searchResults.endpoint| TD_020| PublicSearch|
      |searchResults.endpoint| TD_021| PublicSearch|
      |searchResults.endpoint| TD_023| PublicSearch|
      |searchResults.endpoint| TD_024| PublicSearch|
      |searchResults.endpoint| TD_025| PublicSearch|
      |searchResults.endpoint| TD_026| PublicSearch|
      |searchResults.endpoint| TD_027| PublicSearch|
      |searchResults.endpoint| TD_028| PublicSearch|

  Scenario Outline: Send an invalid POST Request for invalid search
    Given Payload is created with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet |
      |searchResults.endpoint| TD_022| PublicSearch|