package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

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
    this.firstNameLocator.fill(lastName);
  }

  public void fillEmail(String email) {
    this.firstNameLocator.fill(email);
  }

  public void fillPassword(String password) {
    this.firstNameLocator.fill(password);
  }

  public void clickRegister() {
    this.registerButtonLocator.click();
  }


}
