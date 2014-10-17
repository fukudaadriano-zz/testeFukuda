package com.alltax.functionaltest.login;


import java.util.concurrent.TimeUnit;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alltax.functionaltest.login.pageobject.LoginPO;
import com.alltax.functionaltest.utils.PageObjectWrapper;
import com.alltax.functionaltest.utils.WebDriverWrapper;

public class LoginIT {

	private static WebDriverWrapper webDriverWrapper;

	private static PageObjectWrapper<LoginPO> loginPOWrapper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		webDriverWrapper = new WebDriverWrapper(
				WebDriverWrapper.WebDriverBrowser.Firefox);
		webDriverWrapper.getDriver().manage().timeouts()
				.implicitlyWait(20, TimeUnit.SECONDS);
		loginPOWrapper = new PageObjectWrapper<LoginPO>(
				webDriverWrapper.getDriver(), LoginPO.class);
	}

	@Test
	public void efetuaLoginInvalido() throws Exception {
		loginPOWrapper.getPageObject()
				.typeAndEnter("ssss", "yyyy", "Português");
		loginPOWrapper.getPageObject().incorrectUserAndPassword("Português");
	}

	@Test
	public void efetuaLoginValido() throws Exception {
		loginPOWrapper.getPageObject().typeAndEnter("AFUKU", "Afka1988",
				"Português");
	}

	@AfterClass
	public static void tearDown() {
		webDriverWrapper.getDriver().close();
		webDriverWrapper.getDriver().quit();
		webDriverWrapper.setDriver(null);
	}
}
