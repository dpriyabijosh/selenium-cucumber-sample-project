@login
Feature: User Login Authentication
  As a user of the SauceDemo application
  I want to login with different user types
  So that I can access the application features based on my user privileges

  Background:
    Given I am on the SauceDemo login page

  Scenario Outline: Successful login with valid users
    When I login with username "<username>" and password "<password>"
    Then I should be successfully logged in
    And I should see the Products page

    Examples:
      | username                | password     |
      | standard_user          | secret_sauce |
      | problem_user           | secret_sauce |
      | performance_glitch_user| secret_sauce |

  