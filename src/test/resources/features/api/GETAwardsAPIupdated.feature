Feature: GET response using /searchResults/award API

  Scenario Outline: Get a valid Response for valid awardnumber
    Given A award exists with award number from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |awards.endpoint|TD_001|Awards| 200 |
      |awards.endpoint|TD_002|Awards| 200 |
      |awards.endpoint|TD_003|Awards| 200 |
      |awards.endpoint|TD_004|Awards| 200 |
      |awards.endpoint|TD_005|Awards| 200 |
      |awards.endpoint|TD_006|Awards| 200 |
      |awards.endpoint|TD_007|Awards| 200 |
      |awards.endpoint|TD_008|Awards| 200 |
      |awards.endpoint|TD_009|Awards| 200 |
      |awards.endpoint|TD_010|Awards| 200 |
      |awards.endpoint|TD_011|Awards| 200 |
      |awards.endpoint|TD_012|Awards| 200 |
      |awards.endpoint|TD_013|Awards| 200 |
      |awards.endpoint|TD_014|Awards| 200 |
      |awards.endpoint|TD_015|Awards| 200 |

  Scenario Outline: Get a invalid Response for invalid awardnumber
    Given A award exists with award number from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |awards.endpoint|TD_016|Awards| 404 |
      |awards.endpoint|TD_017|Awards| 404 |