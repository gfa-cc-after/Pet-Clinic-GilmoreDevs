Feature: Registration

  Scenario Outline: registration of a new user
    Given A user is on the register page
    When I fill the email field with "{email}"
    And I fill the password field with "{password}"
    And I click on the register button
    Then I should see a success message

    Examples:
      | email                    | password          |
      | "john.doe@gmail.com"     | "AppleTree123"    |
      | "not-john.doe@gmail.com" | "AppleTree123555" |