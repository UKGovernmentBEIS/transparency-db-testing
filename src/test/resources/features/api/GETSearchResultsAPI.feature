Feature: GET request using /accessmanagement/searchresults API

  Scenario Outline: Send a valid GET Request for valid search
    Given Valid search is done with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Get" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |accessmanagementsearchResults.endpoint| TD_001| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_002| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_003| SearchResults| 404 |
      |accessmanagementsearchResults.endpoint| TD_004| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_005| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_006| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_007| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_008| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_009| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_010| SearchResults| 404 |
      |accessmanagementsearchResults.endpoint| TD_011| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_012| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_013| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_014| SearchResults| 200 |
      |accessmanagementsearchResults.endpoint| TD_015| SearchResults| 200 |