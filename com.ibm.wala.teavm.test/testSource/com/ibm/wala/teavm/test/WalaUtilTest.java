package com.ibm.wala.teavm.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

@RunWith(Parameterized.class)
public class WalaUtilTest {
	 
	private static WebDriver driver;

	public static final String USERNAME = System.getenv("SAUCE_USERNAME");
	public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
	
	@BeforeClass
	public static void openBrowser() throws MalformedURLException{
		DesiredCapabilities caps = DesiredCapabilities.firefox();
		caps.setCapability("platform", "macOS 10.12");
		caps.setCapability("version", "54.0");
		String travisJobNumber = System.getenv("TRAVIS_JOB_NUMBER");
		if (travisJobNumber != null && travisJobNumber.length() > 0) {
			caps.setCapability("tunnel-identifier", travisJobNumber);
		}
		
	    driver = new RemoteWebDriver(new URL(URL), caps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("http://localhost:8899/com.ibm.wala.teavm/target/com.ibm.wala.teavm-0.0.1-SNAPSHOT/");
	}

	@AfterClass
	public static void closeBrowser() {
		driver.quit();
	}

	private final int index;

	public WalaUtilTest(int index) {
		this.index = index;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> tests() {
		return Arrays.asList(
				new Object[][] {
					{0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10},
					{11}, {12}, {13}, {14}, {15}, {16}, {17}, {18}, {19}, {20},
					{21}, {22}, {23}, {24}, {25}, {26}, {27}, {28}, {29}, {30},
					{31}, {32}, {33}, {34}, {35}, {36}, {37}, {38}});
	}

	@Test
	public void test() {
		WebElement dat = driver.findElement(By.id("test"));
		dat.clear();
		dat.sendKeys("" + index);
		driver.findElement(By.id("runTest")).click();
		String s = dat.getAttribute("value");
		Assert.assertTrue(s, "1".equals(s));
	}

}
