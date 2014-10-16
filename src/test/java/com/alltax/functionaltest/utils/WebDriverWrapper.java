package com.alltax.functionaltest.utils;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.alltax.functionaltest.login.LoginIT;

public class WebDriverWrapper {

	private WebDriver driver;

	private String ambienteTeste = "http://192.168.1.101:8039/timp/login/#/login";

	// private String ambienteTeste = "http://localhost:8080/sistemapamcard/";

	public enum WebDriverBrowser {
		InternetExplorer("/IEDriverServer.exe"), Chrome("/chromedriver.exe"), Firefox(
				"");

		File driverServer;

		WebDriverBrowser(String pathDriverTest) {
			driverServer = getDriverServerFile(pathDriverTest);
		}

		private static File getDriverServerFile(String pathDriverTest) {
			URL urlLogin = LoginIT.class.getResource(pathDriverTest);
			return new File(urlLogin.getFile());
		}
	}

	public WebDriverWrapper(WebDriverBrowser driverBrowser) {
		driver = getWebDriverInstance(driverBrowser);
		// this.pageObject = PageFactory.initElements(driver, pageObjectClass);
		driver.get(ambienteTeste);
	}

	public WebDriver getDriver() {
		return driver;
	}

	// public T getPageObject() {
	// return pageObject;
	// }

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	// public void setPageObject( Class<T> pageObjectClass) {
	// this.pageObject = PageFactory.initElements(driver, pageObjectClass);
	// }

	private WebDriver getWebDriverInstance(WebDriverBrowser webDriverBrowser) {
		switch (webDriverBrowser) {
		case InternetExplorer: {
			System.setProperty("webdriver.ie.driver",
					webDriverBrowser.driverServer.getAbsolutePath());
			return new InternetExplorerDriver();
		}
		case Chrome: {
			System.setProperty("webdriver.chrome.driver",
					webDriverBrowser.driverServer.getAbsolutePath());
			return new ChromeDriver();
		}
		case Firefox: {
			return new FirefoxDriver();
		}
		default:
			return null;
		}
	}
}
