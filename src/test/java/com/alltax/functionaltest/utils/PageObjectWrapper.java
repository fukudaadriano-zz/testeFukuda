package com.alltax.functionaltest.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageObjectWrapper<T extends UtilizadorWebDriver> {

	private T pageObject;

	public PageObjectWrapper(WebDriver driver, Class<T> pageObjectClass) {
		this.pageObject = PageFactory.initElements(driver, pageObjectClass);
		pageObject.setDriver(driver);
	}

	public T getPageObject() {
		return pageObject;
	}

}
