import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.shaded.org.checkerframework.checker.regex.qual.Regex;
import pages.RegisterPage;
import util.BrowserInstance;

import java.io.File;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Main {
  private static final Faker faker = new Faker();

  // Storing the email and password globally so that it can be reused for login
  private static final String firstName = faker.name().firstName();
  private static final String lastName = faker.name().lastName();
  private static final String email = String.format("%s.%s@gmail.com", firstName, lastName);
  private static final String password = "ValidPassword123";


  public static void main(String[] args) {
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

  public static void registerSuccessful(Page page) {
    page.navigate("http://localhost:5173/register");
    RegisterPage registerPage = new RegisterPage(page);
    registerPage.fillFirstName(firstName);
    registerPage.fillLastName(lastName);
    registerPage.fillEmail(email);
    registerPage.fillPassword(password);
    registerPage.clickRegister();
    registerPage.assertSuccessMessageVisible();
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