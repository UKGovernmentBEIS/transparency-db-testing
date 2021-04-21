Feature: GET response using /searchResults/award API

  Scenario Outline: Get a valid Response for valid awardnumber
    Given A award exists with award number from datasheet by passing "<TestData>" & "<DataSheet>"
    When I call "<Endpoint>" API with "Get" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet |
      |awards.endpoint|TD_001|Awards|
      |awards.endpoint|TD_002|Awards|
      |awards.endpoint|TD_003|Awards|
      |awards.endpoint|TD_004|Awards|
      |awards.endpoint|TD_005|Awards|
      |awards.endpoint|TD_006|Awards|
      |awards.endpoint|TD_007|Awards|
      |awards.endpoint|TD_008|Awards|
      |awards.endpoint|TD_009|Awards|
      |awards.endpoint|TD_010|Awards|
      |awards.endpoint|TD_011|Awards|
      |awards.endpoint|TD_012|Awards|
      |awards.endpoint|TD_013|Awards|
      |awards.endpoint|TD_014|Awards|
      |awards.endpoint|TD_015|Awards|

  Scenario Outline: Get a invalid Response for invalid awardnumber
    Given A award exists with award number from datasheet by passing "<TestData>" & "<DataSheet>"
    When I call "<Endpoint>" API with "Get" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet |
      |awards.endpoint|TD_016|Awards|
      |awards.endpoint|TD_017|Awards|