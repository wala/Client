package com.ibm.wala.teavm.test;

import java.io.File;
import java.net.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.*;

public class WalaTeaVMTest {
	 
	protected static WebDriver driver;

	public static final String USERNAME = System.getenv("SAUCE_USERNAME");
	public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

        public static File page;
    
	@BeforeClass
	public static void openBrowser() throws MalformedURLException {
		/*
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "macOS 10.12");
		caps.setCapability("version", "59.0");
		String travisJobNumber = System.getenv("TRAVIS_JOB_NUMBER");
		if (travisJobNumber != null && travisJobNumber.length() > 0) {
			caps.setCapability("tunnel-identifier", travisJobNumber);
			caps.setCapability("build", System.getenv("TRAVIS_BUILD_NUMBER"));
		}
		
	    driver = new RemoteWebDriver(new URL(URL), caps);*/

	    ChromeOptions options = new ChromeOptions();
	    if (System.getenv("USE_X") == null) {
		options.addArguments("--headless");
	    }
	    options.addArguments("--allow-file-access-from-files");
	    DesiredCapabilities cap = DesiredCapabilities.chrome();
	    cap.setCapability(ChromeOptions.CAPABILITY, options);

	    driver = new RemoteWebDriver(new URL("http://localhost:9515"), cap);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//driver.manage().window().setSize(new Dimension(2048, 2048));

		assert page != null : "provide page for tests";
		
		driver.get("file://" + page.getAbsolutePath());
	}

	@AfterClass
	public static void closeBrowser() {
		driver.quit();
	}
}
