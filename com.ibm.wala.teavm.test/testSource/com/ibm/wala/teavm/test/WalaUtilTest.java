package com.ibm.wala.teavm.test;

import java.io.File;
import java.net.MalformedURLException;
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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RunWith(Parameterized.class)
public class WalaUtilTest extends WalaTeaVMTest {
    static {
	page = new File(System.getProperty("user.dir"), "../com.ibm.wala.teavm/target/com.ibm.wala.teavm-0.0.1-SNAPSHOT/index.html");
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
		WebElement dat = driver.findElement(By.className("StupidTestField"));
		dat.clear();
		dat.sendKeys("" + index);
		driver.findElement(By.id("runTest")).click();
		String s = dat.getAttribute("value");
		Assert.assertTrue(s, "1".equals(s));
	}

}
