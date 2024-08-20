package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import model.RegisterUser;

import static pages.Utils.withName;

public class LoginPage {

  private final Locator email;
  private final Locator password;
  private final Locator loginButton;
  private final Locator loginSuccessMessage;

  public LoginPage(Page page) {
    this.email = page.getByRole(AriaRole.TEXTBOX, withName("email"));
    this.password = page.getByRole(AriaRole.TEXTBOX, withName("password"));
    this.loginButton = page.getByRole(AriaRole.BUTTON, withName("login"));
    //TODO: we are redirected to login, we should rather test that?
    this.loginSuccessMessage = page.getByTestId("login-success-message");
  }

  public void fillWithUser(RegisterUser user) {
    this.email.fill(user.getEmail());
    this.password.fill(user.getPassword());
  }

  public void clickLogin() {
    this.loginButton.click();
  }

  public void assertSuccessMessageVisible() {
    this.loginSuccessMessage.isVisible();
  }
}
