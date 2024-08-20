package e2e.util;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import e2e.config.BrowserConfig;
import org.junit.jupiter.api.BeforeAll;

public class BrowserInstance implements AutoCloseable {

  private final Playwright playwright;
  private final Browser browser;
  protected final Page page;

  public BrowserInstance() {
    this.playwright = Playwright.create();
    this.browser = playwright.chromium().launch(BrowserConfig.BROWSER_CONFIG);
    this.page = this.browser.newPage();
  }

  @Override
  public void close() {
    this.page.close();
    this.browser.close();
    this.playwright.close();
  }

  public Page getPage() {
    return page;
  }
}
