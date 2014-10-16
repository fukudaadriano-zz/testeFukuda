package com.alltax.functionaltest.login.pageobject;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.alltax.functionaltest.utils.UtilizadorWebDriver;

public class LoginPO implements UtilizadorWebDriver {

	@FindBy(css = "input[type=\"text\"]")
	WebElement username;

	@FindBy(css = "input[type=\"password\"]")
	WebElement password;

	@FindBy(id = "login-btn")
	WebElement loginButton;

	@FindBy(xpath = "//div[@id='language']/div/div/div/input")
	WebElement language;

	@FindBy(xpath = "//li")
	WebElement portuguese;

	@FindBy(xpath = "//li[2]")
	WebElement english;

	@FindBy(id = "wrong-user")
	WebElement messageError;

	public String baseWindowHdl;

	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void login() throws Exception {
		typeAndEnter("AFUKU", "Afka1988", "Português");
	}

	public void typeAndEnter(String user, String pass, String idioma) {
		new WebDriverWait(getDriver(), 30).until(ExpectedConditions
				.visibilityOf(language));
		username.clear();
		password.clear();
		username.sendKeys(user);
		password.sendKeys(pass);
		language.click();
		if (idioma.equals("Português")) {
			portuguese.click();
		} else {
			english.click();
		}
		loginButton.click();
	}

	public void incorrectUserAndPassword(String language) {
		new WebDriverWait(getDriver(), 10).until(ExpectedConditions
				.visibilityOf(messageError));
		if (language.equals("Português")) {
			assertEquals("Senha ou usuário inválidos", messageError.getText());
		} else {
			assertEquals("Invalid user or password", messageError.getText());
		}
	}

}
