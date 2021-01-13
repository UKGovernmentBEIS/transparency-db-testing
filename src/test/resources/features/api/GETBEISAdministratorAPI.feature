Feature: GET response using /accessmanagement/beisadmin API

  Scenario Outline: Get a valid Response for Beis administrator
    Given Header is sent with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>
   
    Examples:
      |Endpoint |TestData| DataSheet|StatusCode |
      |beisadmin.endpoint|TD_001|Dashboards| 200 |