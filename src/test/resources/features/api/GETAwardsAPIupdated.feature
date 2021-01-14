Feature: GET response using /searchResults/award API

  Scenario Outline: Get a valid Response for valid awardnumber
    Given A award exists with award number of "<AwardID>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>
    #Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | AwardID | StatusCode |
      |awards.endpoint| AW118 | 200 |


  Scenario Outline: Get a invalid Response for invalid awardnumber
    Given A award exists with award number of "<AwardID>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>
   # Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | AwardID | StatusCode |
      |awards.endpoint| AW1| 404 |
      |awards.endpoint| AW7 | 200 |
      |awards.endpoint| AW6 | 200 |
      |awards.endpoint| AW9 | 200 |
      |awards.endpoint| AW52| 200 |
      |awards.endpoint| AW22| 200 |
      |awards.endpoint| AW41| 200 |
      |awards.endpoint| AW46| 200 |
      |awards.endpoint| AW32| 200 |
      |awards.endpoint| AW45| 200 |
      |awards.endpoint| AW37| 200 |
      |awards.endpoint| AW30| 200 |
      |awards.endpoint| AW35| 200 |
      |awards.endpoint| AW23| 200 |
      |awards.endpoint| AW39| 200 |
      |awards.endpoint| AW33| 200 |
      |awards.endpoint| AW43| 200 |
      |awards.endpoint| AW44| 200 |
      |awards.endpoint| AW36| 200 |
      |awards.endpoint| AW42| 200 |
      |awards.endpoint| AW34| 200 |
      |awards.endpoint| AW38| 200 |
      |awards.endpoint| AW31| 200 |
      |awards.endpoint| AW40| 200 |
      |awards.endpoint| AW28| 200 |
      |awards.endpoint| AW47| 200 |
      |awards.endpoint| AW26| 200 |
      |awards.endpoint| AW50| 200 |
      |awards.endpoint| AW49| 200 |
      |awards.endpoint| AW29| 200 |
      |awards.endpoint| AW48| 200 |
      |awards.endpoint| AW27| 200 |
      |awards.endpoint| AW13| 200 |
      |awards.endpoint| AW25| 200 |
      |awards.endpoint| AW24| 200 |
      |awards.endpoint| AW17| 200 |
      |awards.endpoint| AW15| 200 |
      |awards.endpoint| AW5 | 200 |
      |awards.endpoint| AW10| 200 |
      |awards.endpoint| AW12| 200 |
      |awards.endpoint| AW51| 200 |
      |awards.endpoint| AW8 | 200 |
      |awards.endpoint| AW11| 200 |
      |awards.endpoint| AW20| 200 |
      |awards.endpoint| AW21| 200 |
      |awards.endpoint| AW14| 200 |
      |awards.endpoint| AW18| 200 |
      |awards.endpoint| AW19| 200 |

