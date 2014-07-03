package com.guyi.TestNG;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.guyi.Base.HomePage;
import com.guyi.CarMonitor.CarMonitor;

public class HomePageTest {
	HomePage testPage;
  @BeforeClass
  public void beforeClass() {
	  System.err.println("HomePageTest"+TestNGBase.driver);
	  testPage = new HomePage(TestNGBase.driver);
  }


  @Test
  public void login() {
//    throw new RuntimeException("Test not implemented");
//	Boolean ispass = l.login("test", "test", "http://192.168.1.180:8080/login.htm");
//	Boolean ispass = l.login("test", "test", "http://115.29.198.101:8011/index.htm");
	boolean result= testPage.login("bsuser", "123456", "http://guyi2013.vicp.cc:8011/index.htm");
//			testPage.login("", "", "");
	
  }
}
