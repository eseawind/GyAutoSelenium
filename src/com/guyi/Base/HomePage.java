package com.guyi.Base;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * 首页登录
 * 
 * */
public class HomePage extends BasePage     {
	WebDriver driver;
//	@FindBy(name="userName-inputEl")
//	WebElement username;
//	@FindBy(id="userPwd-inputEl")
//	WebElement password;
	
	public HomePage(WebDriver driver) { 
		super(driver); 
		this.driver = driver;
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public Boolean login(String usr, String pwd, String url) {
		try {
			driver.get(url);
//			System.err.println("login"+driver);			
//			LoginPage page = PageFactory.initElements(driver, LoginPage.class);			
			// 1.登录系统 userName-inputEl
//			System.err.println("test"+driver.findElements(By.id("userName-inputEl")));
			new WebDriverWait(driver, 30).until(new ExpectedCondition<Boolean>() { 
				@Override
				public Boolean apply(WebDriver driver) { 
//					System.err.println("打开登录界面中：");
					log.debug( "打开登录界面中：");
					return driver.findElement(By.id("userName-inputEl")).isDisplayed();
				} 
			});
			WebElement username = this.getElement("username");
			username.clear();			
			username.sendKeys(usr);
			WebElement passwd = this.getElement("passwd");
			passwd.clear();
			passwd.sendKeys(pwd);						
			WebElement loginBTN = this.getElement("loginBTN");
			//点击登录按钮
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",loginBTN);
//			System.err.println("已登录");
			log.info("已登录");
		} catch (Exception e) {
//			System.err.println(e);
			log.error(e);
			return false;
		}
		return true;
	}

	public Boolean loginfail(String usr, String pwd, String url){
		//点击登录按钮
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
						driver.findElement(By.xpath("//span[@class='x-btn-inner x-btn-inner-center']")));
		//判断是否弹出登录失败提示框 
		WebElement loginfail=null;
		try {
			loginfail = new WebDriverWait(driver, 3)
				.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver arg0) {
					  List<WebElement> spans=	arg0.findElements(By
								.xpath("//div[@class='x-window x-message-box x-layer x-window-default x-closable x-window-closable x-window-default-closable']//span"));
//					  System.err.println(spans.size()); 
					  for(WebElement e :spans){
						  if( e.getText().equals("登陆失败!") ){
							  return e;
						  }
					  }
						return null;
					}
				});
		} catch (Exception e) {
			 System.err.println("没找到，登录成功");
			 return false;
		} 
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		WebDriver driver = new FirefoxDriver();
		System.err.println(driver.getClass().getName());
		HomePage l = new HomePage(driver);
		System.err.println(l);				
//		Boolean ispass = l.login("test", "test", "http://192.168.1.180:8080/login.htm");
		Boolean ispass = l.login("test", "test", "http://115.29.198.101:8011/index.htm");
//		Boolean ispass = l.login("test", "test", "http://guyi2013.vicp.cc:8088/index.htm");
		System.err.println(ispass);
		driver.quit();
	}

}
