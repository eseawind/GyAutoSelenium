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
		//��ȡ�������鰴ť
		List<WebElement> weBtns = getDisplayedElements("WarnDetail");  
		//�ж����������
		String strdriverclass = driver.getClass().getName(); 
		if (strdriverclass.equals("org.openqa.selenium.ie.InternetExplorerDriver") ) {
			//IE�Ƚ����� ͨ��js����ִ��
			new Actions(driver).click(weBtns.get(0)).perform();
		}else if (strdriverclass.equals("org.openqa.selenium.firefox.FirefoxDriver")) {
			weBtns.get(0).click();		
		}else {
			weBtns.get(0).click();
		}  
		// TempOil Ϊyaml���ñ�ǩ �ж��¶����������Ƿ���� ������������ʧ��
		if (getElementNoWait("TempOil") == null) { 
			log.error("δ�����¶���������" );
			result = false;
		}else {	
			log.info("�¶�����������ʾ");
		}
		//value:"//div[@id='Gy.ui.system.STerminalWarnOptEditor_header-innerCt']/div/div/img[@class='x-tool-img x-tool-close']"
		sleep(5000);
		WebElement btnclose =getElement("btnClose");	
		System.err.println(btnclose.isDisplayed());
		driver.manage().window().maximize();
		if (!btnclose.isDisplayed()) {
			//����¶����������йرհ�ťû����ʾ �򷵻�ʧ��
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
		wb.log.info("����̨���Խ��:"+re); 
		sleep(30000);
		driver.close();
	}
}
