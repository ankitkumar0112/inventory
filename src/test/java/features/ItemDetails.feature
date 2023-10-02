Feature: ItemDetails

  Scenario: As a user, I want to add   Item Details details to the inventory
    Given User has the permissions to add   Item Details details
    When I provide all the details to the inventory for data creation of   Item Details:
      | name        | description      | media  | supplier_id | id |
      | Electronics | Electronics Item | Videos | 123         | 1  |
    Then The   Item Details Service should return the Id:
      | id |
      | 1  |

  Scenario: As a user, I want to fetch the details from the inventory
    Given User wants to get the details of the   Item Details
    When I provide the   Item Details id for the retrieval:
      | id |
      | 1  |
    Then The inventory should successfully return the value of   Item Details to the user:
      | name        | description      | media  | supplier_id | id |
      | Electronics | Electronics Item | Videos | 123         | 1  |


  Scenario: As a user, I want to update the details of   Item Details from the inventory
    Given User wants to update the details of the   Item Details
    When I provide the   Item Details id and all the details to be updated:
      | name        | description      | media  | supplier_id | id |
      | Electronics | Electronics Item | Videos | 123         | 1  |
    Then The inventory should successfully return the   Item Details id:
      | id |
      | 1  |



