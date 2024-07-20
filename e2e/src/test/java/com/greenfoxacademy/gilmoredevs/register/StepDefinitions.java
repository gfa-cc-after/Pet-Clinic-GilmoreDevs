package com.greenfoxacademy.gilmoredevs.register;

import com.microsoft.playwright.*;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class StepDefinitions {
    private static Page page;
    private static final String REGISTER_URL = "http://localhost:5173/register";
    private static final BrowserType.LaunchOptions launchOptions;
    private static final GenericContainer<?> database;

    static {
        launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setSlowMo(1000);
        launchOptions.setHeadless(false);
    }

    static {
        database = new GenericContainer<>("postgres:16")
                .withExposedPorts(5432)
                .withEnv("POSTGRES_PASSWORD", "some_safe_password")
                .withEnv("POSTGRES_USER", "some_user")
                .withEnv("POSTGRES_DB", "some_db");
    }


    @BeforeAll
    public static void setUp() {
        database.start();
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(launchOptions);
        page = browser.newPage();
    }

    @AfterAll
    public static void tearDown() {
        try {
            page.close();
            database.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("A user is on the register page")
    public void aUserIsOnTheRegisterPage() {
        page.navigate(REGISTER_URL);
    }

    @When("I fill the email field with {string}")
    public void iFillTheEmailFieldWith(String email) {
        page.fill("input[name=email]", email);
    }

    @And("I fill the password field with {string}")
    public void iFillThePasswordFieldWith(String password) {
        page.fill("input[name=password]", password);
    }

    @And("I click on the register button")
    public void iClickOnTheRegisterButton() {
        page.click("button[type=submit]");
    }

    @Then("I should see a success message")
    public void iShouldSeeASuccessMessage() {
        Locator successMessage = page.getByText("successful registration!");
        successMessage.waitFor();
        assertThat(successMessage).isVisible();
    }

    @And("I fill the firstName field with {string}")
    public void iFillTheFirstNameFieldWithFirstName(String firstName) {
        page.fill("input[name=firstName]", firstName);
    }

    @And("I fill the lastName field with {string}")
    public void iFillTheLastNameFieldWithLastName(String lastName) {
        page.fill("input[name=lastName]", lastName);
    }

}
