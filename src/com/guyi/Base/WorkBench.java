package com.guyi.Base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

import com.thoughtworks.selenium.Selenium;

public class WorkBench extends BasePage {

	public WorkBench(WebDriver d) {
		super(d);
	} 
	
	public boolean getWarnDetail(){
		boolean result = true;
		//获取报警详情按钮
		List<WebElement> weBtns = getDisplayedElements("WarnDetail");  
		//判断浏览器类型
		String strdriverclass = driver.getClass().getName(); 
		if (strdriverclass.equals("org.openqa.selenium.ie.InternetExplorerDriver") ) {
			//IE比较特殊 通过js命令执行
			new Actions(driver).click(weBtns.get(0)).perform();
		}else if (strdriverclass.equals("org.openqa.selenium.firefox.FirefoxDriver")) {
			weBtns.get(0).click();		
		}else {
			weBtns.get(0).click();
		}  
		// TempOil 为yaml配置标签 判断温度油量界面是否存在 不存在则用例失败
		if (getElementNoWait("TempOil") == null) { 
			log.error("未弹出温度油量界面" );
			result = false;
		}else {	
			log.info("温度油量正常显示");
		}
		//value:"//div[@id='Gy.ui.system.STerminalWarnOptEditor_header-innerCt']/div/div/img[@class='x-tool-img x-tool-close']"
		sleep(5000);
		WebElement btnclose =getElement("btnClose");	
		System.err.println(btnclose.isDisplayed());
		driver.manage().window().maximize();
		if (!btnclose.isDisplayed()) {
			//如果温度油量界面中关闭按钮没有显示 则返回失败
			return false;
		}
		btnclose.click();
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		WebDriver driver = new FirefoxDriver();
		WebDriver driver = new ChromeDriver();
//		WebDriver driver = new InternetExplorerDriver();
		System.err.println(driver.getClass());
		HomePage l = new HomePage(driver);
		System.err.println(l);				
		Boolean ispass = l.login("test", "test", "http://192.168.1.180:8080/login.htm");
//		Boolean ispass = l.login("test", "test", "http://115.29.198.101:8011/index.htm");
//		Boolean ispass = l.login("test", "test", "http://guyi2013.vicp.cc:8088/index.htm");
		System.err.println(ispass);
		WorkBench wb = new WorkBench(driver);
		boolean re = wb.getWarnDetail();
		wb.log.info("工作台测试结果:"+re); 
		sleep(30000);
		driver.close();
	}
}
