package com.greenfoxacademy.gilmoredevs;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
    private static Browser browserContext;

    @BeforeAll
    public static void setUp() {
        browserContext = TestBrowser.open();
    }

    @AfterAll
    public static void tearDown() {
        browserContext.close();
    }

    @Given("A user is on the register page")
    public void aUserIsOnTheRegisterPage() {

    }

    @When("I fill the email field with {string}")
    public void iFillTheEmailFieldWith(String email) {
    }

    @And("I fill the password field with {string}")
    public void iFillThePasswordFieldWith(String password) {
    }

    @And("I click on the register button")
    public void iClickOnTheRegisterButton() {
    }

    @Then("I should see a success message")
    public void iShouldSeeASuccessMessage() {
    }
}
