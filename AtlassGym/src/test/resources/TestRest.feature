Feature: Rest tests

  Scenario Outline: test create user
    Given I open registration page
    When I input name "<name>"
    And I input familyname "<familyname>"
    And I input password "<password>"
    And If is trainer "<isTrainer>"
    And Check if already created user "<exists>"
    Then I verify user creation

    Examples:
      | name | familyname | password | exists | isTrainer |
      | Dohn | Huan       | 01oe0d   | false  | false     |
      | Neo  | Lokiii     | 6hr3f3   | true   | true      |
      | Mann | Coh        | ytr7fg   | false  | false     |
      | Ronn | Doe        | 652yh5   | false  | false     |

  Scenario Outline: test login function
    Given I log into account
    When I input username "<username>"
    And I input password "<password>"
    And Created user "<exists>"
    Then I verify access to account

    Examples:
      | username   | password | exists |
      | Dohn.Huan  | 01oe0d   | true   |
      | Neo.Lokiii | 6hr3f3   | false  |
      | Mann.Coh   | ytr7fg   | true   |

  Scenario Outline: test add training function
    Given I add training
    When I am logged in "<trainerusername>" as a trainer
    And Trainee "<traineeusername>" exists
    And I input training name "<trainingname>"
    And I input training datetime "<trainingdatetime>"
    And I input training duration "<trainingduration>"
    Then I verify add training

    Examples:
      | trainerusername | traineeusername | trainingname | trainingdatetime | trainingduration |
      | Neo.Lokiii      | Dohn.Huan       | Zoomba       | 2021.08.04 11:30 | 15               |
      | Neo.Lokiii      | Ronn.Doe        | Aerobics     | 2022.11.21 08:45 | 45               |
      | Neo.Lokiii      | Mann.Coh        | Crossfit     | 2023.01.01 20:00 | 60               |