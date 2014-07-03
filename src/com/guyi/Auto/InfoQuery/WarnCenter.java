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
		log.debug("��֤��ѯ����");		
		if (findQueryElement() ) { 			
			log.debug("��ѯԪ�����ҵ���"+startDate);
			startDate.sendKeys(startd);
			startTime.sendKeys(startT);
			endDate.sendKeys(endD);
			endTime.sendKeys(endT);
		}
		log.debug("��֤��������������:");		
		WebElement trigType=getElement("trigType");		
		//��������� ��ȡ��������
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
		log.info("�жϱ������ͣ�"+result);
		for (WebElement li :lis ) {
			log.info(li.getText()+lis.size()); 
			if (li.getText().equals("�����澯")) {
				li.click();
				log.info("��������澯---------------");
			}
		}
	 
		List<WebElement> btns=driver.findElements(By.xpath("//div[contains(@id,'toolbar')]/div[contains(@id,'toolbar')]/div[contains(@id,'toolbar')]//div[contains(@id,'-btnWrap')]/a/span[@class='x-btn-inner x-btn-inner-center']"));
		for (WebElement webElement : btns) {
//			System.err.println("��ťspan��"+webElement.getText());
			log.debug("��ťspan��"+webElement.getText());
			if (webElement.getText().equals("��ѯ")) {
				webElement.click();
				break;
			}
		}
		return true;
	} 
	
	
	//ѡ���� 
	/**
	 * @param menuname ��ҳ���ƣ� ���ڶ����ҳʱ���ж�
	 * @param comparyname �������� 
	 * @param plate ���� 
	 * @return �Ƿ���ȷѡ��
	 * */
	public boolean SelectCar(String menuname,String comparyname,String plate){ 	
		List<WebElement> tabbar=getDisplayedElements("tabbarpath");
		for(WebElement e:tabbar){ 
			//ѡ���ҳ
			if (e.getText().equals(menuname)) {
				 new Actions(driver).click(e).perform();				
			}
		}
		//�ҵ��ұߴ��壬 �̶�һ��
		WebElement fatherDiv ;
		fatherDiv= driver.findElement(By.xpath("/html/body/div[contains(@id,'panel')]"));
		//�ҵ��ұ߿����еĳ����� xpath
		String strXpath= ".//div/table/tbody/tr/td/div/span[@class='x-tree-node-text']"; 		
		List<WebElement> cartrees =fatherDiv.findElements(By.xpath(strXpath));		
		for(WebElement e :cartrees){ 
			this.log.debug(e.getText());
			//���ȴ򿪳���Ŀ¼��  �������� 
			 if (e.getText().equals(comparyname)) {				 
				 new Actions(driver).doubleClick(e).perform();  
			}
		} 
		sleep(1000);
		//������Ӻ�ҳ���ˢ�£������»�ȡһ�� 
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
		w.openMenus("��Ϣ��ѯ-��������"); 
		w.openMenus("��Ϣ��ѯ-�¼�����");
		w.openMenus("��Ϣ��ѯ-��ʷͼ��");
		w.openMenus("��Ϣ����-��Ϣ����");
		w.SelectCar("��������", "��ʢ����", "��A-00011");
//		w.findQueryElement(); 
		w.Query("2014-05-13","00:00:01","2014-06-13","12:00:01");
		System.err.println("end !");
		sleep(30000); 
	}

}
