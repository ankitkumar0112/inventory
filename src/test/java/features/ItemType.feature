Feature: ItemType

  Scenario: As a user, I want to add item type details to the inventory
    Given User has the permissions to add itemType details
    When I provide all the details to the inventory for data creation of Item Type:
      | name        | description      | Classification tag | id |
      | Electronics | Electronics Item | Elex               | 1  |
    Then The ItemType Service should return the Id:
      | id |
      | 1  |

  Scenario: As a user, I want to fetch the details from the inventory
    Given User wants to get the details of the Item Type
    When I provide the Item Type id for the retrieval:
      | id |
      | 1  |
    Then The inventory should successfully return the value of Item Type to the user:
      | name        | description      | Classification tag | id |
      | Electronics | Electronics Item | Elex               | 1  |


  Scenario: As a user, I want to update the details of Item Type from the inventory
    Given User wants to update the details of the itemType
    When I provide the Item Type id and all the details to be updated:
      | name       | description     | Classification tag | id |
      | Electrical | Electrical Item | Elex               | 1  |
    Then The inventory should successfully return the Item Type id:
      | id |
      | 1  |

  Scenario: As a user, I want to delete the Item Type details from the inventory
    Given User wants to delete the Item Type details of the item
    When I provide the Item Type id to be deleted:
      | id |
      | 1  |
    Then The inventory should successfully return the message for Item Type:
      | message |
      | Success |

