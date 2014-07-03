package com.guyi.TestNG;

import org.bouncycastle.jcajce.provider.symmetric.ARC4.Base;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.Assertion;

import com.guyi.CarMonitor.CarMonitor;

public class CarMonitorTest {
	CarMonitor testPage;
	
  @BeforeClass
  public void beforeClass() {
	  testPage = new CarMonitor(TestNGBase.driver);
  }

  @BeforeTest
  public void beforeTest() {
	  
  }

  @Test
  public void openMenus() {
	  boolean result =testPage.openMenus("车辆监控-车辆监控2");
		new Assertion().assertEquals(result, true, "QueryGroupMileage");
  }
  
  @Test(dependsOnMethods="openMenus")
  public void SelectCar() {
	  boolean result =testPage.SelectCar("车辆监控2", "合利物流", "闽A-00011");
		new Assertion().assertEquals(result, true, "QueryGroupMileage");
  }
  
  @Test(dependsOnMethods="SelectCar")
  public void QueryGroupMileage() {
	  boolean result =testPage.QueryGroupMileage("2014-06-25", "", "2014-07-01","12:00:11");
		new Assertion().assertEquals(result, true, "QueryGroupMileage");
  }
 

}
