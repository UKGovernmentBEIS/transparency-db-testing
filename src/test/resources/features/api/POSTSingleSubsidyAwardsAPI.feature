Feature: POST request using /addAward API

  @valid
  Scenario Outline: Send a valid POST Request for adding Single subsidy award
    Given Payload is created with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet

    Examples:
      |Endpoint | TestData | DataSheet |
      |addsinglesubsidyaward.endpoint|TD_001|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_002|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_003|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_004|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_005|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_006|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_007|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_008|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_009|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_010|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_011|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_012|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_013|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_014|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_015|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_016|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_017|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_018|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_019|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_020|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_021|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_022|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_023|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_024|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_025|AddSingleNewSubsidyAward|

  @Invalid
  Scenario Outline: Send an invalid POST Request for adding Single subsidy award
    Given Payload is created with details from datasheet by passing "<TestData>" & "<DataSheet>"
    When I calls "<Endpoint>" API with "Post" http request
    Then I will be getting the expected StatusCode
    Then I will be validating response against values in datasheet

    Examples:
      |Endpoint | TestData | DataSheet |
      |addsinglesubsidyaward.endpoint|TD_026|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_027|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_028|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_029|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_030|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_031|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_032|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_033|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_034|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_035|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_036|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_037|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_038|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_039|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_040|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_041|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_042|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_043|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_044|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_045|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_046|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_047|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_048|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_049|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_050|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_051|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_052|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_053|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_054|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_055|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_056|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_057|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_058|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_059|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_060|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_061|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_062|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_063|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_064|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_065|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_066|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_067|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_068|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_069|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_070|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_071|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_072|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_073|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_074|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_075|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_076|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_077|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_078|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_079|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_080|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_081|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_082|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_083|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_084|AddSingleNewSubsidyAward|
      |addsinglesubsidyaward.endpoint|TD_085|AddSingleNewSubsidyAward|