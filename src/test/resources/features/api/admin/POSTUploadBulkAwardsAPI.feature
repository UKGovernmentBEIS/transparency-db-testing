Feature: POST request using /uploadBulkAwards API

  @valid
  Scenario Outline: Send a valid POST Request for bulkupload subsidy awards
    Given Bulkupload sheet "<TestData>" & "<DataSheet>" is updated with "valid" subsidy award details
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet

    Examples:
      |Endpoint | TestData | DataSheet |
      |bulkupload.endpoint| USTD_001| BulkUploadValidations|

  @invalid
  Scenario Outline: Send an invalid POST Request for bulkupload subsidy awards
    Given Bulkupload sheet "<TestData>" & "<DataSheet>" is updated with "invalid" subsidy award details
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet

    Examples:
      |Endpoint | TestData | DataSheet |
      |bulkupload.endpoint| USTD_002| BulkUploadValidations|