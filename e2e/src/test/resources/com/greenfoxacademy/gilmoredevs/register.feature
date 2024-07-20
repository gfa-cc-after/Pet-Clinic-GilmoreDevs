Feature: Registration

  Scenario Outline: registration of a new user
    Given A user is on the register page
    When I fill the email field with <email>
    And I fill the password field with <password>
    And I fill the firstName field with <firstName>
    And I fill the lastName field with <lastName>
    And I click on the register button
    Then I should see a success message

    Examples:
      | email                    | password          | firstName | lastName |
      | "john.doe@gmail.com"     | "AppleTree123"    | "John"    | "Doe"    |
      | "not-john.doe@gmail.com" | "AppleTree123555" | "John"    | "Doe"    |