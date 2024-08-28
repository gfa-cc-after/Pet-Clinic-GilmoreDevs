package e2e.config;

import com.microsoft.playwright.BrowserType;

/**
 * Browser configuration class
 * update this class to change the browser configuration everywhere in the project
 *
 * @see BrowserType.LaunchOptions
 */
public class BrowserConfig {
  /**
   * Headless mode
   * true means headless mode, not opening the browser
   */
  public static final boolean HEADLESS = true;
  /**
   * Slow motion value in milliseconds
   * 0 means no slow motion
   */
  public static final int SLOW_MO = 0;

  public static final BrowserType.LaunchOptions BROWSER_CONFIG = new BrowserType.LaunchOptions();

  static {
    BROWSER_CONFIG.setHeadless(HEADLESS);
    BROWSER_CONFIG.setSlowMo(SLOW_MO);
  }

}
