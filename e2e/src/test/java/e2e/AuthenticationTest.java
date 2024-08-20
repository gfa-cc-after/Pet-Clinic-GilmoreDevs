package e2e;

import com.github.javafaker.Faker;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import e2e.model.RegisterUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import e2e.pages.LoginPage;
import e2e.pages.RegisterPage;
import e2e.util.BrowserInstance;

public class AuthenticationTest {
  private static final String HOME_URL = "http://localhost:5173";
  private static final Faker FAKER = new Faker();

  private static final RegisterUser USER = new RegisterUser(FAKER, "ValidPassword123");

  private static BrowserInstance browser;
  private static Page page;

  @BeforeAll
  public static void setup() {
    browser = new BrowserInstance();
    page = browser.getPage();
  }

  @AfterAll
  public static void tearDown() {
    page.close();
    browser.close();
  }

  @DisplayName("When user registers they can login too")
  @Test
  public void registerAndLoginWithSameCredentialsSuccessful() {
    page.navigate(HOME_URL + "/register");
    RegisterPage registerPage = new RegisterPage(page);
    registerPage.fillWithUser(USER);
    registerPage.clickRegister();
    registerPage.assertSuccessMessageVisible();
    page.navigate(HOME_URL + "/login");
    LoginPage loginPage = new LoginPage(page);
    loginPage.fillWithUser(USER);
    loginPage.clickLogin();
    loginPage.assertSuccessMessageVisible();
  }

}