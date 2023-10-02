Feature: Classification

  Scenario: As a user, I want to add classification details of an Item to the inventory
    Given User has the permissions to add classification details
    When I provide all the details to the inventory for data creation:
      | name        | description      | tag  | id |
      | Electronics | Electronics Item | Elex | 1  |
    Then The Classification Service should return the Classification Id:
      | id |
      | 1  |

  Scenario: As a user, I want to fetch the details from the inventory
    Given User wants to get the details of the item
    When I provide the item id for the retrieval:
      | id |
      | 1  |
    Then The inventory should successfully return the value to the user:
      | name        | description      | tag  | id |
      | Electronics | Electronics Item | Elex | 1  |


  Scenario: As a user, I want to update the details from the inventory
    Given User wants to update the details of the item
    When I provide the item id and all the details to be updated:
      | name       | description     | tag  | id |
      | Electrical | Electrical Item | Elex | 1  |
    Then The inventory should successfully return the Classification id:
      | id |
      | 1  |

  Scenario: As a user, I want to delete the details from the inventory
    Given User wants to delete the details of the item
    When I provide the classification id to be deleted:
      | id |
      | 1  |
    Then The inventory should successfully return the message:
      | message |
      | Success |

