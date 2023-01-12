package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.HomePage;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest {
	HomePage homePage;
	LoginPage loginPage;
	InputStream dataIs;
	JSONObject loginUsers;

	@BeforeClass
	public void beforeClass() throws Exception {
		try {
			String userDataFile = "data/loginUsers.json";
			dataIs = getClass().getClassLoader().getResourceAsStream(userDataFile);
			JSONTokener tokener = new JSONTokener(dataIs);
			loginUsers = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		} finally {
			if (dataIs != null)
				dataIs.close();
		}
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		homePage = new HomePage();
		System.out.println(m.getName());
	}

	@AfterMethod
	public void afterMethod() {
	}

	@Test
	public void invalidUserName() {
		homePage.clickMenuBtn();
		loginPage = homePage.clickMenuLoginBtn();
		loginPage.enterUsername(loginUsers.getJSONObject("invalidUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
		loginPage.pressLoginBtn();

		String actualErrTxt = loginPage.getErrTxt();
		String expectedErrTxt = strings.get("err_invalid_username_or_password");
		Assert.assertEquals(actualErrTxt, expectedErrTxt);

	}

	@Test
	public void invalidPassword() {
		homePage.clickMenuBtn();
		loginPage = homePage.clickMenuLoginBtn();
		loginPage.enterUsername(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		loginPage.pressLoginBtn();
		String actualErrTxt = loginPage.getErrTxt();
		String expectedErrTxt = strings.get("err_invalid_username_or_password");

		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	}

	@Test
	public void validLogin() throws InterruptedException {
		homePage.clickMenuBtn();
		loginPage = homePage.clickMenuLoginBtn();
		loginPage.enterUsername(loginUsers.getJSONObject("validLogin").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("validLogin").getString("password"));
		ProductsPage productsPage = loginPage.pressLoginBtn();
		Thread.sleep(1000);
		String actualTxt = productsPage.getProductTitle();
		String expectedTxt = strings.get("products_title");

		Assert.assertEquals(actualTxt, expectedTxt);

	}

}
