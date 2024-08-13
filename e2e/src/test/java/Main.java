import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Main {
  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
      launchOptions.setSlowMo(2000);
      launchOptions.setHeadless(false);
      Browser browser = playwright.chromium().launch(launchOptions);
      Page page = browser.newPage();
      page.navigate("http://localhost:5173");
      System.out.println(page.title());
    }
  }
}