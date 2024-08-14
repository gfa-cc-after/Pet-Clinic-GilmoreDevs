import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.shaded.org.checkerframework.checker.regex.qual.Regex;

import java.io.File;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Main {
  private static Faker faker = new Faker();

  public static void main(String[] args) throws Exception {
    try (Playwright playwright = Playwright.create()) {
      BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
      launchOptions.setSlowMo(2000);
      launchOptions.setHeadless(false);
      Browser browser = playwright.chromium().launch(launchOptions);
      Page page = browser.newPage();

      registerSuccessful(page);
    }
  }

  public static void registerSuccessful(Page page) throws Exception {
    page.navigate("http://localhost:5173/register");

    // On the register page
    // fill firstName
    Page.GetByRoleOptions getByRoleOptionsFirstname = new Page.GetByRoleOptions();
    getByRoleOptionsFirstname.setName("firstname");
    Locator firstnameInputLocator = page.getByRole(AriaRole.TEXTBOX, getByRoleOptionsFirstname);
    String firstname = faker.name().firstName();
    firstnameInputLocator.fill(firstname);

    Page.GetByRoleOptions getByRoleOptionsLastName = new Page.GetByRoleOptions();
    getByRoleOptionsLastName.setName("lastname");
    Locator lastnameInputLocator = page.getByRole(AriaRole.TEXTBOX, getByRoleOptionsLastName);
    String lastName = faker.name().lastName();
    lastnameInputLocator.fill(lastName);

    Page.GetByRoleOptions getByRoleOptionsEmail = new Page.GetByRoleOptions();
    getByRoleOptionsEmail.setName("email");
    Locator emailInputLocator = page.getByRole(AriaRole.TEXTBOX, getByRoleOptionsEmail);
    String email = firstname.concat(".").concat(lastName).concat("@gmail.com");
    emailInputLocator.fill(email);

    Locator passwordInputLocator = page.getByTestId("pass-testid");
    passwordInputLocator.fill("ValidPassword123");

    //screen.getByRole('button', {  name: /register/i})
    Page.GetByRoleOptions getByRoleOptionsRegisterButton = new Page.GetByRoleOptions();
    getByRoleOptionsRegisterButton.setName("register");
    Locator registerButtonInputLocator = page.getByRole(AriaRole.BUTTON, getByRoleOptionsRegisterButton);
    registerButtonInputLocator.click();

    // Successful registration

    Locator registrationSuccessMessage = page.getByTestId("register-success-message");
    assertThat(registrationSuccessMessage).isVisible();
  }
}