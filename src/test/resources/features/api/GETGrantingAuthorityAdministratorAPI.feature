Feature: GET response using /accessmanagement/gaadmin API

  Scenario Outline: Get a valid Response for Granting Authority Administrator
    Given Header is sent with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>

    Examples:
      |Endpoint |TestData| DataSheet|StatusCode |
      |gaadmin.endpoint|TD_002|Dashboards| 200 |