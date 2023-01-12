package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;

public class TestListener implements ITestListener{
	
	
	
	
	public void onTestFailure(ITestResult result) {
		if(result.getThrowable()!=null) {
			StringWriter sw=new StringWriter();
			PrintWriter pw=new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			System.out.println(sw.toString());
		}
		
		BaseTest baseTest=new BaseTest();
		File file=baseTest.getDriver().getScreenshotAs(OutputType.FILE);
		Map<String,String> params=new HashMap<String,String>();
		params=result.getTestContext().getCurrentXmlTest().getAllParameters();
		
		

		String imagePath="Screenshots"+File.separator+params.get("platformName")+ "_" + params.get("platformVersion")+ "_" +
				params.get("deviceName") + File.separator + baseTest.getDateTime() + File.separator +
				result.getTestClass().getRealClass().getSimpleName()+File.separator +
				result.getName() + ".png";
		
		System.out.println(imagePath);
		
		String completeImagePath=System.getProperty("user.dir")+ File.separator + imagePath;
		
		System.out.println(completeImagePath);
		
		try {
			FileUtils.copyFile(file, new File(imagePath));
			Reporter.log("This is the screenshot of failed Test Script");
			Reporter.log("<a href='"+completeImagePath+"'><img src='"+completeImagePath+"' height='300' width='300'/></a>");
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}

}
