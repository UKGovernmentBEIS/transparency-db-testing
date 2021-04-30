Feature: GET request using /accessmanagement/searchresults API

  Scenario Outline: Send a valid GET Request for valid search
    Given Valid search is done with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I call "<Endpoint>" API with "Get" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet |
      |accessmanagementsearchResults.endpoint| TD_001| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_002| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_003| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_004| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_005| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_006| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_007| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_008| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_009| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_010| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_011| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_012| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_013| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_014| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_015| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_016| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_017| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_018| SearchResults|
      |accessmanagementsearchResults.endpoint| TD_019| SearchResults|
