import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import model.RegisterUser;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.shaded.org.checkerframework.checker.regex.qual.Regex;
import pages.LoginPage;
import pages.RegisterPage;
import util.BrowserInstance;

import java.io.File;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Main {
  private static final String HOME_URL = "http://localhost:5173";
  private static final Faker FAKER = new Faker();

  private static final RegisterUser USER = new RegisterUser(FAKER, "ValidPassword123");
  private static final BrowserType.LaunchOptions LAUNCH_OPTIONS = new BrowserType.LaunchOptions();

  static {
    LAUNCH_OPTIONS.setHeadless(false);
    LAUNCH_OPTIONS.setSlowMo(1000);
  }

  public static void main(String[] args) {
    try (BrowserInstance instance = new BrowserInstance(LAUNCH_OPTIONS); Page page = instance.getPage()) {
      registerSuccessful(page);
      loginSuccessful(page);
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    }
  }

  public static void registerSuccessful(Page page) {
    page.navigate(HOME_URL + "/register");
    RegisterPage registerPage = new RegisterPage(page);
    registerPage.fillWithUser(USER);
    registerPage.clickRegister();
    registerPage.assertSuccessMessageVisible();
  }

  public static void loginSuccessful(Page page) {
    page.navigate(HOME_URL + "/login");
    LoginPage loginPage = new LoginPage(page);
    loginPage.fillWithUser(USER);
    loginPage.clickLogin();
    loginPage.assertSuccessMessageVisible();
  }
}