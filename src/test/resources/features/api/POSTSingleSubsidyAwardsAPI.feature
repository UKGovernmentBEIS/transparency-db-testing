Feature: POST request using /addAward API

  @valid
  Scenario Outline: Send a valid POST Request for adding Single subsidy award
    Given Payload is created with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet

    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |addsinglesubsidyaward.endpoint|TD_001|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_002|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_003|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_004|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_005|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_006|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_007|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_008|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_009|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_010|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_011|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_012|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_013|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_014|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_015|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_016|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_017|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_018|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_019|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_020|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_021|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_022|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_023|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_024|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_025|AddSingleNewSubsidyAward|200|

  @Invalid
  Scenario Outline: Send an invalid POST Request for adding Single subsidy award
    Given Payload is created with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected <StatusCode>
    Then I will be validating response against values in datasheet

    Examples:
      |Endpoint | TestData | DataSheet | StatusCode |
      |addsinglesubsidyaward.endpoint|TD_031|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_033|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_036|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_038|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_043|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_044|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_045|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_046|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_047|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_048|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_049|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_050|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_060|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_061|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_062|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_064|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_065|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_068|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_071|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_073|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_074|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_076|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_077|AddSingleNewSubsidyAward|200|