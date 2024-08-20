package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static pages.Utils.withName;

public class LoginPage {

  private final Locator emailLocator;
  private final Locator passwordLocator;
  private final Locator loginButtonLocator;

  public LoginPage(Page page) {
    this.emailLocator = page.getByRole(AriaRole.TEXTBOX, withName("email"));
    this.passwordLocator = page.getByRole(AriaRole.TEXTBOX, withName("password"));
    this.loginButtonLocator = page.getByRole(AriaRole.BUTTON, withName("login"));
  }

}
