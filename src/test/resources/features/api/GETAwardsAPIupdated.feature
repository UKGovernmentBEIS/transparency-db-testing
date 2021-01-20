Feature: GET response using /searchResults/award API

  Scenario Outline: Get a valid Response for valid awardnumber
    Given A award exists with award number of "<AwardID>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | AwardID | StatusCode |
      |awards.endpoint| AW2701| 200 |
      |awards.endpoint| AW2702| 200 |
      |awards.endpoint| AW2708| 200 |
      |awards.endpoint| AW2709| 200 |
      |awards.endpoint| AW2710| 200 |
      |awards.endpoint| AW2717| 200 |
      |awards.endpoint| AW2718| 200 |
      |awards.endpoint| AW2719| 200 |
      |awards.endpoint| AW2705| 200 |
      |awards.endpoint| AW2704| 200 |
      |awards.endpoint| AW2726| 200 |

  Scenario Outline: Get a invalid Response for invalid awardnumber
    Given A award exists with award number of "<AwardID>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | AwardID | StatusCode |
      |awards.endpoint| AW1| 404 |
      |awards.endpoint| AW2| 404 |


