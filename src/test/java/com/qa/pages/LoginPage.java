package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;


import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BaseTest{

	@AndroidFindBy (accessibility="Username input field") 
	@iOSXCUITFindBy (accessibility="Username input field")
	private WebElement usernameTxtFld;
	
	@AndroidFindBy (accessibility="Password input field") 
	@iOSXCUITFindBy (accessibility="Password input field")
	private WebElement passwordTxtFld;
	
	@AndroidFindBy (accessibility="Login button") 
	@iOSXCUITFindBy (accessibility="Login button")
	private WebElement loginBtn; 
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc='generic-error-message']/android.widget.TextView") 
	@iOSXCUITFindBy (xpath="//XCUIElementTypeOther[@name=\"generic-error-message\"]/child::XCUIElementTypeStaticText")
	private WebElement errTxt; 
	
	public LoginPage enterUsername(String userName) {
		sendKeys(usernameTxtFld,userName);
		return this;
	}
	
	public LoginPage enterPassword(String password) {
		sendKeys(passwordTxtFld,password);
		return this;
	}
	
	public ProductsPage pressLoginBtn() {
		click(loginBtn);
		return new ProductsPage();
		
	}
	
	public String getErrTxt() {
		return getText(errTxt);
	}
	
}


