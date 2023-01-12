package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;


import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage extends BaseTest{
	
	@AndroidFindBy (accessibility="open menu") 
	@iOSXCUITFindBy (accessibility="tab bar option menu")
	private WebElement menuBtn;
	
	@AndroidFindBy (accessibility="menu item log in") 
	@iOSXCUITFindBy (accessibility="menu item log in")
	private WebElement menuLoginBtn;
	
	
	public HomePage clickMenuBtn() {
		click(menuBtn);
		return this;
	}
	
	public LoginPage clickMenuLoginBtn() {
		click(menuLoginBtn);
		return new LoginPage();
	}

}
