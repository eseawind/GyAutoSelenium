package test;

import junit.framework.Test;

import org.openqa.selenium.WebDriver;

public class TestThreadLocal {
	public static ThreadLocal<String> ThreadDriver=new ThreadLocal<String>() ;
	public TestThreadLocal() {
		 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestThreadLocal.ThreadDriver.set("123");
		TestThreadLocal.ThreadDriver.set("abc");
		System.err.println(		TestThreadLocal.ThreadDriver.get());
		for (int i = 0; i < 100; i++) { 
			TestThreadLocal.ThreadDriver.set("abc"+i);
			System.err.println(TestThreadLocal.ThreadDriver.get() + TestThreadLocal.ThreadDriver);			
		}
	}

}
