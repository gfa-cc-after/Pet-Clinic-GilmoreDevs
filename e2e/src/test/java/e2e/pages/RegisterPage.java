package e2e.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import e2e.model.RegisterUser;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static e2e.util.Utils.withName;

public class RegisterPage {

  private final Locator firstName;
  private final Locator lastName;
  private final Locator email;
  private final Locator password;
  private final Locator registerButton;
  private final Locator registerSuccessMessage;

  public RegisterPage(final Page page) {
    this.firstName = page.getByRole(AriaRole.TEXTBOX, withName("firstname"));
    this.lastName = page.getByRole(AriaRole.TEXTBOX, withName("lastName"));
    this.email = page.getByRole(AriaRole.TEXTBOX, withName("email"));
    this.password = page.getByTestId("pass-testid");
    this.registerButton = page.getByRole(AriaRole.BUTTON, withName("register"));
    this.registerSuccessMessage = page.getByTestId("register-success-message");
  }

  public void fillWithUser(RegisterUser user) {
    this.firstName.fill(user.getFirstName());
    this.lastName.fill(user.getLastName());
    this.email.fill(user.getEmail());
    this.password.fill(user.getPassword());
  }

  public void clickRegister() {
    this.registerButton.click();
  }

  public void assertSuccessMessageVisible() {
    assertThat(this.registerSuccessMessage).isVisible();
  }
}
