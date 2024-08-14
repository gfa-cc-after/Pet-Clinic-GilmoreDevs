import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

public class Main {


  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(new File("../docker-compose.yaml"));
      dockerComposeContainer.start();
      BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
      launchOptions.setSlowMo(2000);
      launchOptions.setHeadless(false);
      Browser browser = playwright.chromium().launch(launchOptions);
      Page page = browser.newPage();
      page.navigate("http://localhost:5173");
      System.out.println(page.title());
      Page.GetByRoleOptions getByRoleOptions = new Page.GetByRoleOptions();
      getByRoleOptions.setName("Register");
      Locator registerButtonLocator = page.getByRole(AriaRole.LINK, getByRoleOptions);
      registerButtonLocator.click();
      //getByRole('textbox', { name: /firstname/i })

      Page.GetByRoleOptions getByRoleOptionsFirstname = new Page.GetByRoleOptions();
      getByRoleOptionsFirstname.setName("firstname");
      Locator firstnameInputLocator = page.getByRole(AriaRole.TEXTBOX, getByRoleOptionsFirstname);
      firstnameInputLocator.fill("Mark");
      dockerComposeContainer.stop();
    }
  }
}