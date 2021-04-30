Feature: GET response using /accessmanagement/gaadmin API

  Scenario Outline: Get a valid Response for Granting Authority Administrator
    Given Header is sent with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I call "<Endpoint>" API with "Get" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet

    Examples:
      |Endpoint |TestData| DataSheet|
      |gaadmin.endpoint|TD_002|Dashboards|