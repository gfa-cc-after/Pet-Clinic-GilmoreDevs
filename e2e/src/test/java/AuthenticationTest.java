import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import model.RegisterUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.RegisterPage;
import util.BrowserInstance;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class AuthenticationTest {
  private static final String HOME_URL = "http://localhost:5173";
  private static final Faker FAKER = new Faker();

  private static final RegisterUser USER = new RegisterUser(FAKER, "ValidPassword123");
  private static final BrowserType.LaunchOptions LAUNCH_OPTIONS = new BrowserType.LaunchOptions();

  static {
    LAUNCH_OPTIONS.setHeadless(false);
    LAUNCH_OPTIONS.setSlowMo(1000);
  }

  private static BrowserInstance browserInstance;
  private static Page page;

  @BeforeAll
  public static void setup() {
    browserInstance = new BrowserInstance(LAUNCH_OPTIONS);
    page = browserInstance.getPage();
  }

  @AfterAll
  public static void tearDown() throws Exception {
    page.close();
    browserInstance.close();
  }

  @DisplayName("When user registers they can login too")
  @Test
  public void registerSuccessful() {
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