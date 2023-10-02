Feature: Stock

  Scenario: As a user, I want to add Stock to the inventory
    Given User has the permissions to add Stock
    When I provide all the details to the inventory for data creation of Stock:
      | locationCode | availableQty | id |
      | 123          | 10           | 1  |
    Then The Stock Service should return the Id:
      | id |
      | 1  |

  Scenario: As a user, I want to fetch the details from the inventory
    Given User wants to get the details of the Stock
    When I provide the   Stock id for the retrieval:
      | id |
      | 1  |
    Then The inventory should successfully return the value of Stock to the user:
      | locationCode | availableQty | id |
      | 123          | 10           | 1  |


  Scenario: As a user, I want to update the details of Stock from the inventory
    Given User wants to update the details of the Stock
    When I provide the   Stock id and all the details to be updated:
      | locationCode | availableQty | id |
      | 123          | 10           | 1  |
    Then The inventory should successfully return the Stock id:
      | id |
      | 1  |


