package com.greenfoxacademy.gilmoredevs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

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
