Feature: Registration
  Scenario: Create user
    Given i navigate to the website
    And i enter an email and password
    When i click sign up
    Then my account will be created

  Scenario: Long username
    Given i navigate to the website
    And i enter a very long username
    When i click sign up
    Then it will tell me to use a shorter username

  Scenario: Username already in use
    Given i navigate to the website
    And i enter a name that is already in use
    When i click sign up
    Then it will tell me that it is in use

  Scenario: missing email
    Given i navigate to the website
    And i enter a username and password
    When i click sign up
    Then it will tell me to add @ to my address