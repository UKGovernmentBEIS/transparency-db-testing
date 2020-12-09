Feature: Search by Beneficiary
  Scenario Outline: PBI-465 Public User Search - Search by Beneficiary
    Given a Public User is on the Public User Search Portal,
    When the Public User specifies a Beneficiary name "<TestData>" from "<DataSheet>"
    Then a search is returned that filters by the selected Beneficiary name.
  Examples:
    |TestData| DataSheet|
    |USTD_003| PublicSearch|