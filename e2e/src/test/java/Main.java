import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.shaded.org.checkerframework.checker.regex.qual.Regex;
import util.BrowserInstance;

import java.io.File;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Main {
  private static Faker faker = new Faker();

  // Storing the email and password globally so that it can be reused for login
  private static String email;
  private static final String password = "ValidPassword123"; // Password is fixed

  public static void main(String[] args) throws Exception {
    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
    launchOptions.setHeadless(false);
    launchOptions.setSlowMo(1000);
    try (BrowserInstance instance = new BrowserInstance(launchOptions); Page page = instance.getPage()) {

      registerSuccessful(page);
      loginSuccessful(page);
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
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

    email = firstname.concat(".").concat(lastName).concat("@gmail.com");
    Page.GetByRoleOptions getByRoleOptionsEmail = new Page.GetByRoleOptions();
    getByRoleOptionsEmail.setName("email");
    Locator emailInputLocator = page.getByRole(AriaRole.TEXTBOX, getByRoleOptionsEmail);
    emailInputLocator.fill(email);

    Locator passwordInputLocator = page.getByTestId("pass-testid");
    passwordInputLocator.fill(password);

    //screen.getByRole('button', {  name: /register/i})
    Page.GetByRoleOptions getByRoleOptionsRegisterButton = new Page.GetByRoleOptions();
    getByRoleOptionsRegisterButton.setName("register");
    Locator registerButtonInputLocator = page.getByRole(AriaRole.BUTTON, getByRoleOptionsRegisterButton);
    registerButtonInputLocator.click();

    // Successful registration

    Locator registrationSuccessMessage = page.getByTestId("register-success-message");
    assertThat(registrationSuccessMessage).isVisible();
  }

  public static void loginSuccessful(Page page) throws Exception {
    page.navigate("http://localhost:5173/login");

    Page.GetByRoleOptions getByRoleOptionsEmail = new Page.GetByRoleOptions();
    getByRoleOptionsEmail.setName("email");
    Locator emailInputLocator = page.getByRole(AriaRole.TEXTBOX, getByRoleOptionsEmail);
    emailInputLocator.fill(email);

    Locator passwordInputLocator = page.getByTestId("pass-testid");
    passwordInputLocator.fill(password);

    Page.GetByRoleOptions getByRoleOptionsLoginButton = new Page.GetByRoleOptions();
    getByRoleOptionsLoginButton.setName("login");
    Locator loginButtonInputLocator = page.getByRole(AriaRole.BUTTON, getByRoleOptionsLoginButton);
    loginButtonInputLocator.click();

    Locator loginSuccessMessage = page.getByTestId("login-success-message");
    assertThat(loginSuccessMessage).isVisible();
  }
}