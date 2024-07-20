package com.greenfoxacademy.gilmoredevs.register;

import com.greenfoxacademy.gilmoredevs.TestBrowser;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class StepDefinitions {
    private static Page page;
    private static final String REGISTER_URL = "http://localhost:8080/register";
    private static final BrowserType.LaunchOptions launchOptions;

    static {
        launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setSlowMo(1000);
        launchOptions.setHeadless(false);
    }


    @BeforeAll
    public static void setUp() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(launchOptions);
        page = browser.newPage();
    }

    @AfterAll
    public static void tearDown() {
        try {
            page.close();
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
        page.waitForSelector("div.alert-success");
    }
}
