package com.guyi.Auto.InfoQuery;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.guyi.Base.BasePage;
import com.guyi.Base.HomePage;
import com.guyi.BaseConfig.WarnType;

public class WarnCenter extends BasePage {
 	List<WebElement> menus;	 
	WebElement startDate,startTime,endDate ,endTime;
	public WarnCenter(WebDriver driver) {
		super(driver); 
		// driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
	}  

	private boolean findQueryElement(){
		try {
//			System.err.println("findQueryElements:"+driver.findElements(By.xpath("//table[contains(@id,'startDate')]/tbody/tr/td//input[contains(@id,'startDate-input')]")));
//			List<WebElement> lisstartDate = driver.findElements(By.xpath("//table[contains(@id,'startDate')]/tbody/tr/td//input[contains(@id,'startDate-input')]"));
			List<WebElement> lisstartDate = getDisplayedElements("lisstartDate");
			for (WebElement iterator :lisstartDate) { 
				 if (iterator.isDisplayed()) {
					 startDate=iterator;
				}  
			} 
//			 startDate = driver.findElement(By.xpath("//table[contains(@id,'startDate')]/tbody/tr/td//input[contains(@id,'startDate-input')]"));
//			 startTime = driver.findElement(By.xpath("//table[contains(@id,'startTime')]/tbody/tr/td//input[contains(@id,'startTime-input')]"));
			List<WebElement> lisstartTime = driver.findElements(By.xpath("//table[contains(@id,'startTime')]/tbody/tr/td//input[contains(@id,'startTime-input')]"));
			for (WebElement iterator :lisstartTime) { 
				 if (iterator.isDisplayed()) {
					 startTime=iterator;
				}
			}
//			 endDate = driver.findElement(By.xpath("//table[contains(@id,'endDate')]/tbody/tr/td//input[contains(@id,'endDate-input')]"));
			List<WebElement> lisendDate = driver.findElements(By.xpath("//table[contains(@id,'endDate')]/tbody/tr/td//input[contains(@id,'endDate-input')]"));
			for (WebElement iterator :lisendDate) { 
				 if (iterator.isDisplayed()) {
					 endDate=iterator;
				}  
			}
 //			 endTime = driver.findElement(By.xpath("//table[contains(@id,'endTime')]/tbody/tr/td//input[contains(@id,'endTime-input')]"));
			List<WebElement> lisendTime = driver.findElements(By.xpath("//table[contains(@id,'endTime')]/tbody/tr/td//input[contains(@id,'endTime-input')]"));
			for (WebElement iterator :lisendTime) { 
				 if (iterator.isDisplayed()) {
					 endTime=iterator;
				}  
			}			
			 return true;			
		} catch (Exception e) {
			System.err.println("findQueryElement:"+e);
			return false;
		}
	}
	public boolean Query(String startd,String startT,String endD,String endT){
		boolean result = true;
		log.debug("验证查询功能");		
		if (findQueryElement() ) { 			
			log.debug("查询元素已找到："+startDate);
			startDate.sendKeys(startd);
			startTime.sendKeys(startT);
			endDate.sendKeys(endD);
			endTime.sendKeys(endT);
		}
		log.debug("验证报警类型下拉框:");		
		WebElement trigType=getElement("trigType");		
		//点击下拉框 获取报警类型
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", trigType);
		List<WebElement> lis = new WebDriverWait(driver, 5).until(new ExpectedCondition<List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver driver){
				List<WebElement> lis_tmp = driver.findElements(By.xpath("//ul[@class='x-list-plain']/li"));
				if (lis_tmp.get(0).getText().equals("")) {
					return null;
				}
				return lis_tmp;
			}
		});
		  
		result = new WarnType().checkWarntype(lis);
		log.info("判断报警类型："+result);
		for (WebElement li :lis ) {
			log.info(li.getText()+lis.size()); 
			if (li.getText().equals("油量告警")) {
				li.click();
				log.info("点击油量告警---------------");
			}
		}
	 
		List<WebElement> btns=driver.findElements(By.xpath("//div[contains(@id,'toolbar')]/div[contains(@id,'toolbar')]/div[contains(@id,'toolbar')]//div[contains(@id,'-btnWrap')]/a/span[@class='x-btn-inner x-btn-inner-center']"));
		for (WebElement webElement : btns) {
//			System.err.println("按钮span："+webElement.getText());
			log.debug("按钮span："+webElement.getText());
			if (webElement.getText().equals("查询")) {
				webElement.click();
				break;
			}
		}
		return true;
	} 
	
	
	//选择车辆 
	/**
	 * @param menuname 分页名称： 存在多个分页时做判断
	 * @param comparyname 车队名称 
	 * @param plate 车牌 
	 * @return 是否正确选择
	 * */
	public boolean SelectCar(String menuname,String comparyname,String plate){ 	
		List<WebElement> tabbar=getDisplayedElements("tabbarpath");
		for(WebElement e:tabbar){ 
			//选择分页
			if (e.getText().equals(menuname)) {
				 new Actions(driver).click(e).perform();				
			}
		}
		//找到右边窗体， 固定一个
		WebElement fatherDiv ;
		fatherDiv= driver.findElement(By.xpath("/html/body/div[contains(@id,'panel')]"));
		//找到右边框体中的车辆树 xpath
		String strXpath= ".//div/table/tbody/tr/td/div/span[@class='x-tree-node-text']"; 		
		List<WebElement> cartrees =fatherDiv.findElements(By.xpath(strXpath));		
		for(WebElement e :cartrees){ 
			this.log.debug(e.getText());
			//首先打开车队目录树  车队名称 
			 if (e.getText().equals(comparyname)) {				 
				 new Actions(driver).doubleClick(e).perform();  
			}
		} 
		sleep(1000);
		//点击车队后，页面会刷新，需重新获取一次 
		cartrees =fatherDiv.findElements(By.xpath(".//tr/td/div/span"));
		for(WebElement e :cartrees){ 
			log.debug(e.getText());
			 if (e.getText().equals(plate)) {  
				 new Actions(driver).click(e).perform(); 
			}
		} 
		return true;
	} 
	/**
	 * @param args   MileageStatistics
	 */
	public static void main(String[] args) { 
		WebDriver d2 = new FirefoxDriver();	 
		HomePage a = new HomePage(d2);
		a.login("test", "test", "http://192.168.1.180:8080/login.htm");
//		a.login("test", "test", "http://guyi2013.vicp.cc:8088/login.htm");
//		a.login("test", "test", "http://guyi2013.vicp.cc:8011/");
		System.err.println("-----------");
		WarnCenter w = new WarnCenter(d2);
		w.openMenus("信息查询-报警中心"); 
		w.openMenus("信息查询-事件报告");
		w.openMenus("信息查询-历史图像");
		w.openMenus("信息服务-信息推送");
		w.SelectCar("报警中心", "百盛物流", "闽A-00011");
//		w.findQueryElement(); 
		w.Query("2014-05-13","00:00:01","2014-06-13","12:00:01");
		System.err.println("end !");
		sleep(30000); 
	}

}
