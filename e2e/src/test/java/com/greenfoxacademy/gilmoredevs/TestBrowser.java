package com.greenfoxacademy.gilmoredevs;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class TestBrowser {
    private static Playwright playwright;
    private static final BrowserType.LaunchOptions options;

    static {
        options = new BrowserType.LaunchOptions();
        options.setHeadless(false);
    }

    public static Browser open() {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        return playwright.chromium().launch(options);
    }

    public static void close() {
        playwright.close();
    }

}
