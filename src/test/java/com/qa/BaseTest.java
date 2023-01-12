package com.qa;

import org.testng.annotations.Test;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Base64;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import io.appium.java_client.screenrecording.CanRecordScreen;


public class BaseTest {
	
	protected static AppiumDriver driver;
	protected static String platform;
	protected static Properties props;
	protected static String dateTime;
	InputStream configIs;
	InputStream stringIs;
	protected static HashMap<String,String> strings=new HashMap<String,String>();
	TestUtils testUtils;
	
	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@BeforeMethod
	public void beforeMethod() {
		((CanRecordScreen)driver).startRecordingScreen();
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		Map<String,String> params=new HashMap<String,String>();
		String media=((CanRecordScreen)driver).stopRecordingScreen();
		params=result.getTestContext().getCurrentXmlTest().getAllParameters();
		String dir="video" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName") + 
				File.separator + dateTime +
				File.separator + result.getTestClass().getRealClass().getSimpleName() ;
		
		File videoDir=new File(dir);
		
		if(!videoDir.exists()) {
			videoDir.mkdirs();
		}
		
		FileOutputStream fos = new FileOutputStream(videoDir+ File.separator+ result.getName() + ".mp4");
	    fos.write(Base64.getDecoder().decode(media));
				
	}
  
  @Parameters({"emulator","platformName","platformVersion","deviceName","udid"})
  @BeforeTest
  public void beforeTest(String emulator, String platformName, String platformVersion, String deviceName,String udid) throws Exception {
	  platform=platformName;
	  testUtils=new TestUtils();
	  dateTime=testUtils.getDateTime();
	  
	  try {
		  
		  
		  String propFileName="config.properties";
		  configIs=getClass().getClassLoader().getResourceAsStream(propFileName);
		  props=new Properties();
		  props.load(configIs);
		  
		  String xmlFileName="strings/strings.xml";
		  stringIs=getClass().getClassLoader().getResourceAsStream(xmlFileName);
		  
		  strings=testUtils.parseStringXML(stringIs);
		  
		  DesiredCapabilities caps=new DesiredCapabilities();
		  
	      caps.setCapability(MobileCapabilityType.PLATFORM_NAME,platformName);
	      caps.setCapability("platformVersion",platformVersion);
	      caps.setCapability(MobileCapabilityType.DEVICE_NAME,deviceName);
	      caps.setCapability("newCommandTimeout", 180);
	      caps.setCapability("udid", udid);
	      
	      URL url=new URL(props.getProperty("appiumURL"));
	      
	      switch(platformName) {
	      case "Android" :
		      caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("adroidAutomationName"));
		      caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
		      caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
		      
		      if(emulator.equalsIgnoreCase("true")) {
		    	  caps.setCapability("avd",deviceName);
		    	  caps.setCapability("avdLaunchTimeout", 180000);  //time is in milli-seconds
		      }
		      String androidAppLocation=System.getProperty("user.dir") + File.separator + "src" +
		              File.separator+"test"+File.separator+"resources"+ File.separator + 
		              props.getProperty("androidAppLocation");
		      
		      //caps.setCapability(MobileCapabilityType.APP,androidAppLocation);
		      driver=new AndroidDriver(url,caps);
		      break;
	    	  
	      case "iOS" :
	    	   caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("iOSAutomationName"));
	    	   caps.setCapability("bundleId", props.getProperty("iOSBundleId"));
	    	   String iOSAppLocation=System.getProperty("user.dir") + File.separator + "src" +
			              File.separator+"test"+File.separator+"resources"+ File.separator + 
			             props.getProperty("iOSAppLocation");
	    	   
			      caps.setCapability(MobileCapabilityType.APP,iOSAppLocation);
			      driver=new IOSDriver(url,caps);
			      break;
		 default:
			 throw new Exception("The platform Name value is incorrect" + platformName);    	  
	      }
	      
	      //caps.setCapability(MobileCapabilityType.UDID,"2107f8f1");

	      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  
	  }
	  catch(Exception e){
		  e.printStackTrace();
		  throw e;
	  }
	  finally {
		  if(configIs!=null)
			  configIs.close();
		  if(stringIs!=null)
			  stringIs.close();
	  }
	 
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }
  
  public AppiumDriver getDriver() {
	  return driver;
  }
  
  public String getDateTime() {
	  return dateTime;
  }
  
  public void waitForVisibility(WebElement e) {
	  WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(TestUtils.WAIT));
	  wait.until(ExpectedConditions.visibilityOf(e));
	  
  }
  
  public void click(WebElement e) {
	  waitForVisibility(e);
	  e.click();
  }
  
  public void sendKeys(WebElement e, String txt) {
	  waitForVisibility(e);
	  e.clear();
	  e.sendKeys(txt);
  }
  
  public String getText(WebElement e) {
	  waitForVisibility(e);
	  switch(platform) {
	  case "Android":
		  return e.getAttribute("text");
		 
	  case "iOS":
		  return e.getAttribute("label");
	  }
	  return null;
  }

}
