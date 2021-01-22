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
      |addsinglesubsidyaward.endpoint|TD_026|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_027|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_028|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_029|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_030|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_031|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_032|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_033|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_034|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_035|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_036|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_037|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_038|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_039|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_040|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_041|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_042|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_043|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_044|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_045|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_046|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_047|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_048|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_049|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_050|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_051|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_052|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_053|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_054|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_055|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_056|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_057|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_058|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_059|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_060|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_061|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_062|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_063|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_064|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_065|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_066|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_067|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_068|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_069|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_070|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_071|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_072|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_073|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_074|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_075|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_076|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_077|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_078|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_079|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_080|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_081|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_082|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_083|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_084|AddSingleNewSubsidyAward|200|
      |addsinglesubsidyaward.endpoint|TD_085|AddSingleNewSubsidyAward|200|