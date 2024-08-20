package e2e.util;

import com.microsoft.playwright.Page;

public class Utils {

  public static Page.GetByRoleOptions withName(String name) {
    Page.GetByRoleOptions option = new Page.GetByRoleOptions();
    option.setName(name);
    return option;
  }
}
