Feature: Rest tests

  Scenario Outline: test create user
    Given I open registration page
    When I input name "<name>", familyname "<familyname>", password "<password>" and if is trainer "<isTrainer>"
    And Check if already created user "<exists>"
    Then I verify user creation

    Examples:
      | name | familyname | password | exists | isTrainer |
      | Dohn | Huan       | 01oe0d   | false  | false     |
      | Neo  | Lokiii     | 6hr3f3   | true   | true      |
      | Mann | Coh        | ytr7fg   | false  | false     |
      | Ronn | Doe        | 652yh5   | false  | false     |

  Scenario Outline: test login function
    Given I log into account with username "<username>" and password "<password>"
    And Created user "<exists>"
    Then I verify access to account

    Examples:
      | username   | password | exists |
      | Neo.Lokiii | 6hr3f3   | true   |

  Scenario Outline: test add training function
    Given I add training
    When I am logged in "<trainerusername>" as a trainer
    And Trainee "<traineeusername>" exists
    And I input training name "<trainingname>", training datetime "<trainingdatetime>", training duration "<trainingduration>"
    Then I verify add training

    Examples:
      | trainerusername | traineeusername | trainingname | trainingdatetime | trainingduration |
      | Neo.Lokiii      | Dohn.Huan       | Zoomba       | 2021-08-04       | 15               |
      | Neo.Lokiii      | Ronn.Doe        | Aerobics     | 2022-11-21       | 45               |
      | Neo.Lokiii      | Mann.Coh        | Crossfit     | 2023-01-01       | 60               |

  Scenario Outline: test check trainings workload function
    Given I send request to get trainings workload
    When I am logged in as trainer "<trainerusername>"
    Then I verify check trainings workload

    Examples:
      | trainerusername |
      | Neo.Lokiii      |