package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;


import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductsPage extends BaseTest{
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc='container header']/android.widget.TextView")
	@iOSXCUITFindBy (xpath="//XCUIElementTypeStaticText[@name=\"Products\"]")
	private WebElement productsTitleTxt;

	public String getProductTitle() {
		return getText(productsTitleTxt);
		
	}

}
