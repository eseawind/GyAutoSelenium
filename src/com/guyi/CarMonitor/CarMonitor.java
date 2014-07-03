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


	//ѡ���� 
	/**
	 * @param menuname ��ҳ���ƣ� ���ڶ����ҳʱ���ж�
	 * @param comparyname �������� 
	 * @param plate ���� 
	 * @return �Ƿ���ȷѡ��
	 * */
	public boolean SelectCar(String menuname,String comparyname,String plate){ 	
		boolean result = true;
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
			if (e.getText().equals("�¶�����")) {
				e.click();
			}
		}

			// TempOil Ϊyaml���ñ�ǩ �ж��¶����������Ƿ���� ������������ʧ��
			if (getElementNoWait("TempOil") == null) { 
				log.error("δ�����¶���������");
				result = false;
			}else {	
				log.info("�¶�����������ʾ");
			}
			sleep(2000);
			WebElement btnclose =getElement("btnClose");	
//			System.err.println(btnclose.isDisplayed());
			driver.manage().window().maximize();
			if (!btnclose.isDisplayed()) {
				//����¶����������йرհ�ťû����ʾ �򷵻�ʧ��
				return false;
			}
			btnclose.click();
			if( getElementNoWait("TempOil") != null ){
				log.info("��������ر�ʧ��");
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
			if (e.getText().equals("�ֶ����")) {
				e.click();
			}
		}
		WebElement elementSdate = getElement("startdate");
		elementSdate.clear();
		elementSdate.sendKeys(startDate); //���ÿ�ʼ����
		WebElement elementStime = getElement("starttime");
		elementStime.clear();
		elementStime.sendKeys(Starttime);  //���ÿ�ʼʱ��
		
		WebElement elementEdate = getElement("enddate");
		elementEdate.clear();
		elementEdate.sendKeys(endDate);	//����end����
		WebElement elementEtime = getElement("endtime");
		elementEtime.clear();
		elementEtime.sendKeys(endTime);  	//���ý���ʱ��
		List<WebElement> btnquery = getAllElements("btnGroupMileage");
		for(WebElement e : btnquery){
			System.err.println(e.getText());
			if (e.getText().equals("��ѯ")) {
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
		cm.openMenus("�������-�������2");
		cm.SelectCar("�������2", "��������", "��A-00011");
		cm.QueryGroupMileage("2014-06-25", "", "2014-07-01","12:00:11");
		
		
	}

}
