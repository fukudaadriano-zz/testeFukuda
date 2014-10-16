package com.alltax.functionaltest.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.alltax.functionaltest.login.pageobject.LoginPO;

public class AlltaxTestBase {

	protected static WebDriverWrapper driverWrapper;

	protected static PageObjectWrapper<LoginPO> loginPOWrapper;

	public AlltaxTestBase() {
	}

	@Rule
	public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driverWrapper = new WebDriverWrapper(
				WebDriverWrapper.WebDriverBrowser.Firefox);
		driverWrapper.getDriver().manage().timeouts()
				.implicitlyWait(20, TimeUnit.SECONDS);
		loginPOWrapper = new PageObjectWrapper<LoginPO>(
				driverWrapper.getDriver(), LoginPO.class);
		loginPOWrapper.getPageObject().login();
	}

	@AfterClass
	public static void tearDown() {
		// internetExplorerWebDriverWrapper.getDriver().close();
		driverWrapper.getDriver().quit();
		driverWrapper.setDriver(null);
	}

	public static void takeScreenshot(String fileName) {
		File scrFile = ((TakesScreenshot) driverWrapper.getDriver())
				.getScreenshotAs(OutputType.FILE);
		Date data = new Date();
		try {
			FileUtils.copyFile(scrFile, new File("C:\\Pamcard\\" + fileName
					+ data.getTime() + ".jpeg"), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class ScreenshotTestRule implements MethodRule {
		public Statement apply(final Statement statement,
				final FrameworkMethod frameworkMethod, final Object o) {
			return new Statement() {
				@Override
				public void evaluate() throws Throwable {
					try {
						statement.evaluate();
					} catch (Throwable t) {
						captureScreenshot(frameworkMethod.getName());
						throw t; // rethrow to allow the failure to be reported
									// to JUnit
					}
				}

				public void captureScreenshot(String fileName) {
					try {
						new File("target/surefire-reports/").mkdirs(); // Insure
																		// directory
																		// is
																		// there
						FileOutputStream out = new FileOutputStream(
								"target/surefire-reports/screenshot-"
										+ fileName + ".png");
						out.write(((TakesScreenshot) driverWrapper.getDriver())
								.getScreenshotAs(OutputType.BYTES));
						out.close();
					} catch (Exception e) {
						// No need to crash the tests if the screenshot fails
					}
				}
			};
		}
	}

}
