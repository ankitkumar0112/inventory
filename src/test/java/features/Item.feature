Feature: Item

  Scenario: As a user, I want to add   Item to the inventory
    Given User has the permissions to add Item
    When I provide all the details to the inventory for data creation of   Item:
      | code | category         | company_id | id |
      | 123  | Electronics Item | 123        | 1  |
    Then The Item Service should return the Id:
      | id |
      | 1  |

  Scenario: As a user, I want to fetch the details from the inventory
    Given User wants to get the details of the Item
    When I provide the Item id for the retrieval:
      | id |
      | 1  |
    Then The inventory should successfully return the value of Item to the user:
      | code | category         | company_id | id |
      | 123    | Electronics Item | 123        | 1  |


  Scenario: As a user, I want to update the details of   Item from the inventory
    Given User wants to update the details of the Item
    When I provide the   Item id and all the details to be updated:
      | code | category         | company_id | id |
      | 123    | Electronics Item | 123        | 1  |
    Then The inventory should successfully return the Item id:
      | id |
      | 1  |

  Scenario: As a user, I want to delete the Item from the inventory
    Given User wants to delete the   Item of the item
    When I provide the   Item id to be deleted:
      | id |
      | 1  |
    Then The inventory should successfully return the message for Item:
      | message |
      | Success |

