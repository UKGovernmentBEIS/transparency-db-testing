Feature: PUT request using /accessmanagement API

  Scenario Outline: Send a valid PUT Request for changing status.
    Given Payload is created with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Put" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet
    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |approvalworkflow.endpoint| TD_001| ApprovalWorkflow| 200 |
      |approvalworkflow.endpoint| TD_002| ApprovalWorkflow| 200 |
      |approvalworkflow.endpoint| TD_003| ApprovalWorkflow| 200 |
      #|approvalworkflow.endpoint| TD_002| ApprovalWorkflow| 200 |
