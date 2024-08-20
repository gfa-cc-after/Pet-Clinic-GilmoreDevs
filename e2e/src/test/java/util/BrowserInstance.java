package util;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BrowserInstance implements AutoCloseable {
  private final Playwright playwright;
  private final Browser browser;
  private final Page page;

  public BrowserInstance() {
    this.playwright = Playwright.create();
    this.browser = playwright.chromium().launch();
    this.page = this.browser.newPage();
  }

  public BrowserInstance(BrowserType.LaunchOptions options) {
    this.playwright = Playwright.create();
    this.browser = playwright.chromium().launch(options);
    this.page = this.browser.newPage();
  }

  @Override
  public void close() throws Exception {
    this.page.close();
    this.browser.close();
    this.playwright.close();
  }

}
