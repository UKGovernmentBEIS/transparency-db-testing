Feature: GET response using /accessmanagement/beisadmin API

  Scenario : Get a valid Response for Beis admin
    Given Header is sent with following details
    |SYSTEM|password123|Granting Authority Administrator|123|HMRC|
    When I calls "Endpoint" API with "Get" http request
    Then I will be getting the expected "StatusCode"