package com.guyi.TestNG;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import com.guyi.Base.BasePage;

public class TestNGBase {
  static WebDriver driver ;
  static BasePage basepage ;
  @Test
  public void createDriver() {
	  if(TestNGBase.driver == null ){
		  driver = new FirefoxDriver();
		  System.err.println("test TestNGBase---------"+driver);
	  } 
	  driver.manage().window().maximize();
  }
  @BeforeTest
  public void beforeTest() { 
	  
  }

  @AfterTest
  public void afterTest() {
 
  }

  @BeforeSuite
  public void beforeSuite() {
	  System.err.println("11111"); 
  }

  @AfterSuite
  public void afterSuite() {
	  System.err.println("¹Ø±Õdriver"); 
	  driver.quit();
  }

}
