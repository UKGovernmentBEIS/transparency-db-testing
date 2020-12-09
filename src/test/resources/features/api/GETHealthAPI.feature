Feature: GET request using /health API
  Scenario Outline: For health check
    Given an "<Endpoint>" to check the health status
    When I send a GET Health request
    Then I will be getting the successful <StatusCode> for GET Health
    Examples:
      | Endpoint | StatusCode |
      | health.endpoint |200 |