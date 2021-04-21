Feature: Search Results

  Scenario Outline: Public User Search - Search Results Page
  # This scenario covers Search by the following fields
  # PBI:465 - Public User Search: Search by Beneficiary
  # PBI:527 - Public User Search: Search by Subsidy Instrument
  # PBI:526 - Public User Search: Search by Spending Sector
  # PBI:528 - Public User Search: Search by Subsidy Objective
  # PBI:354 - Public User Search: Search by Legal Granting Date
  # PBI:522 - Public User Search: Multiple Field Search
  # PBI:537 - Public Search Webpage Display Filter
  # PBI:538 - Public Search Webpage Display Sort
  # PBI-784 - Public User Search: Search Results Page

    Given I have "<TestData>" from "<DataSheet>"
    Given I navigate to Search Portal
    When I enter the search criteria
    Then I will be able to get the relevant search results

  Examples:
    |TestData| DataSheet|
    |USTD_001| PublicSearch|
    |USTD_002| PublicSearch|
    |USTD_003| PublicSearch|
    |USTD_004| PublicSearch|
    |USTD_005| PublicSearch|
    |USTD_006| PublicSearch|
    |USTD_007| PublicSearch|
    |USTD_008| PublicSearch|
    |USTD_009| PublicSearch|
    |USTD_010| PublicSearch|
    |USTD_011| PublicSearch|
    |USTD_012| PublicSearch|
    |USTD_013| PublicSearch|
    |USTD_014| PublicSearch|
    |USTD_015| PublicSearch|
    |USTD_016| PublicSearch|
    |USTD_017| PublicSearch|
    |USTD_018| PublicSearch|
    |USTD_019| PublicSearch|
    |USTD_020| PublicSearch|
    |USTD_021| PublicSearch|
    |USTD_022| PublicSearch|
    |USTD_023| PublicSearch|
    |USTD_024| PublicSearch|
    |USTD_025| PublicSearch|
    |USTD_026| PublicSearch|
    |USTD_027| PublicSearch|
    |USTD_028| PublicSearch|
    |USTD_029| PublicSearch|
    |USTD_030| PublicSearch|
    |USTD_031| PublicSearch|
    |USTD_040| PublicSearch|
    |USTD_041| PublicSearch|
    |USTD_042| PublicSearch|
    |USTD_043| PublicSearch|
    |USTD_044| PublicSearch|
    |USTD_045| PublicSearch|


  Scenario Outline: Public User Search - Search Results Details Page

    Given I have "<TestData>" from "<DataSheet>"
    Given I navigate to Search Portal
    When I enter the search criteria
    Then I will be able to validate details page

    Examples:
      |TestData| DataSheet|
      |USTD_034| PublicSearch|
      |USTD_035| PublicSearch|
      |USTD_036| PublicSearch|
      |USTD_037| PublicSearch|
      |USTD_038| PublicSearch|
      |USTD_039| PublicSearch|
