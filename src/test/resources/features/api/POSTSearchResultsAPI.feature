
Feature: POST request using /searchresult API

  Scenario Outline: Send a valid POST Request for valid search
     Given a POST "<Endpoint>"
    When I send a request with JSON body from "<PayloadPath>"
     Then I will be getting the expected <StatusCode>
    Examples:
      | Endpoint | PayloadPath | StatusCode |
      | searchResults.endpoint | ./src/test/resources/payloads/valid-search-criteria.json | 200 |
      | searchResults.endpoint | ./target/test-classes/payloads/invalid-search-criteria.json | 404 |

#  Scenario Outline: Send a POST Request for an empty search
#   Given a POST "<Endpoint>"
#   When I send a request with Empty JSON body
#   Then I will be getting the expected <StatusCode>
#   Examples:
#      | Endpoint | StatusCode |
#      | searchResults.endpoint | 200 |