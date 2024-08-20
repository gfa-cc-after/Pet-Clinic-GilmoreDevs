package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import model.RegisterUser;

import static pages.Utils.withName;

public class LoginPage {

  private final Locator emailLocator;
  private final Locator passwordLocator;
  private final Locator loginButtonLocator;
  private final Locator loginSuccessMessageLocator;

  public LoginPage(Page page) {
    this.emailLocator = page.getByRole(AriaRole.TEXTBOX, withName("email"));
    this.passwordLocator = page.getByRole(AriaRole.TEXTBOX, withName("password"));
    this.loginButtonLocator = page.getByRole(AriaRole.BUTTON, withName("login"));
    //TODO: we are redirected to login, we should rather test that?
    this.loginSuccessMessageLocator = page.getByTestId("login-success-message");
  }

  public void fillWithUser(RegisterUser user) {
    this.emailLocator.fill(user.getEmail());
    this.passwordLocator.fill(user.getPassword());
  }

  public void clickLogin() {
    this.loginButtonLocator.click();
  }

  public void assertSuccessMessageVisible() {
    this.loginSuccessMessageLocator.isVisible();
  }
}
