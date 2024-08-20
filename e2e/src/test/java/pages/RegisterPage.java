package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegisterPage {
  private final Page page;

  private final Locator firstNameLocator;
  private final Locator lastNameLocator;
  private final Locator emailLocator;
  private final Locator passwordLocator;
  private final Locator registerButtonLocator;
  private final Locator registerSuccessMessageLocator;

  public RegisterPage(final Page page) {
    this.page = page;
    this.firstNameLocator = page.getByRole(AriaRole.TEXTBOX, withName("firstname"));
    this.lastNameLocator = page.getByRole(AriaRole.TEXTBOX, withName("lastName"));
    this.emailLocator = page.getByRole(AriaRole.TEXTBOX, withName("email"));
    this.passwordLocator = page.getByTestId("pass-testid");
    this.registerButtonLocator = page.getByRole(AriaRole.BUTTON, withName("register"));
    this.registerSuccessMessageLocator = page.getByTestId("register-success-message");
  }

  private Page.GetByRoleOptions withName(String name) {
    Page.GetByRoleOptions option = new Page.GetByRoleOptions();
    option.setName(name);
    return option;
  }

  public void fillFirstName(String firstName) {
    this.firstNameLocator.fill(firstName);
  }

  public void fillLastName(String lastName) {
    this.lastNameLocator.fill(lastName);
  }

  public void fillEmail(String email) {
    this.emailLocator.fill(email);
  }

  public void fillPassword(String password) {
    this.passwordLocator.fill(password);
  }

  public void clickRegister() {
    this.registerButtonLocator.click();
  }

  public void assertSuccessMessageVisible() {
    assertThat(this.registerSuccessMessageLocator).isVisible();
  }
}
