Feature: GET response using /accessmanagement/gaencoder API

  Scenario Outline: Get a valid Response for Granting Authority Encoder
    Given Header is sent with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet

    Examples:
      |Endpoint |TestData| DataSheet|StatusCode |
      |gaencoder.endpoint|TD_004|Dashboards| 200 |