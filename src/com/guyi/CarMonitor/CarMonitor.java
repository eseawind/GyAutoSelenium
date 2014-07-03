package com.guyi.CarMonitor;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.guyi.Base.BasePage;
import com.guyi.Base.HomePage;

public class CarMonitor extends BasePage {

	public CarMonitor(WebDriver d) {
		super(d);
		// TODO Auto-generated constructor stub
	}


	//选择车辆 
	/**
	 * @param menuname 分页名称： 存在多个分页时做判断
	 * @param comparyname 车队名称 
	 * @param plate 车牌 
	 * @return 是否正确选择
	 * */
	public boolean SelectCar(String menuname,String comparyname,String plate){ 	
		boolean result = true;
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
				 sleep(4000);
				 new Actions(driver).click(e).perform(); 
//				}
			}
		}
		cartrees =getAllElements("CarTrees");
		 System.err.println(cartrees);
		 System.err.println(cartrees.get(1));
		 new Actions(driver).contextClick(cartrees.get(4)).perform(); 
		 sleep(1000);
		 List<WebElement> menutrees = getAllElements("menuTemplateAndOil");
		 System.err.println(menutrees );
		 for (WebElement e : menutrees) {
			System.err.println(e.getText());
			if (e.getText().equals("温度油量")) {
				e.click();
			}
		}

			// TempOil 为yaml配置标签 判断温度油量界面是否存在 不存在则用例失败
			if (getElementNoWait("TempOil") == null) { 
				log.error("未弹出温度油量界面");
				result = false;
			}else {	
				log.info("温度油量正常显示");
			}
			sleep(2000);
			WebElement btnclose =getElement("btnClose");	
//			System.err.println(btnclose.isDisplayed());
			driver.manage().window().maximize();
			if (!btnclose.isDisplayed()) {
				//如果温度油量界面中关闭按钮没有显示 则返回失败
				return false;
			}
			btnclose.click();
			if( getElementNoWait("TempOil") != null ){
				log.info("油量界面关闭失败");
				result = false;
				return result;
			}
		return result;
	}  

	public boolean contextMenu(){
		
		return true;
	}
	
	public boolean QueryGroupMileage(String startDate,String Starttime,String endDate,String endTime){
		boolean result = true;
		List<WebElement> tabs= getAllElements("GroupMileageTabName");
		for(WebElement e : tabs){
//			System.err.println(e.getText());
			if (e.getText().equals("分段里程")) {
				e.click();
			}
		}
		WebElement elementSdate = getElement("startdate");
		elementSdate.clear();
		elementSdate.sendKeys(startDate); //设置开始日期
		WebElement elementStime = getElement("starttime");
		elementStime.clear();
		elementStime.sendKeys(Starttime);  //设置开始时间
		
		WebElement elementEdate = getElement("enddate");
		elementEdate.clear();
		elementEdate.sendKeys(endDate);	//设置end日期
		WebElement elementEtime = getElement("endtime");
		elementEtime.clear();
		elementEtime.sendKeys(endTime);  	//设置结束时间
		List<WebElement> btnquery = getAllElements("btnGroupMileage");
		for(WebElement e : btnquery){
			System.err.println(e.getText());
			if (e.getText().equals("查询")) {
				e.click();				
			}
		}
		System.err.println(btnquery );
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.err.println("11");
		WebDriver d = new FirefoxDriver();
		d.manage().window().maximize();
		HomePage hp = new HomePage(d);
//		d.get("http://192.168.1.180:8080/index.htm");
//		WebElement e =hp.getElement("username");
//		new Actions(d).contextClick(e).perform();
//		hp.login("bsuser", "123456","http://192.168.1.180:8080/index.htm");
		hp.login("bsuser", "123456","http://guyi2013.vicp.cc:8011/index.htm");
		CarMonitor cm = new CarMonitor(d);
		cm.openMenus("车辆监控-车辆监控2");
		cm.SelectCar("车辆监控2", "合利物流", "闽A-00011");
		cm.QueryGroupMileage("2014-06-25", "", "2014-07-01","12:00:11");
		
		
	}

}
