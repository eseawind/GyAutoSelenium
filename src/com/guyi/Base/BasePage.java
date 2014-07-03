package com.guyi.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ho.yaml.Yaml;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.guyi.BaseConfig.Config;
import org.apache.log4j.Logger;

public class BasePage {
	public WebDriver driver;
	// ����Ĭ�ϵȴ�ʱ��
	int waittime;
	// ����Ԫ��
	HashMap<String, HashMap<String, String>> ml;

	public Logger log;
	public BasePage(WebDriver d) {
		// TODO Auto-generated constructor stub
		driver = d;
		waittime = 20;
		// ��ȡyaml�ļ� ����ֵ��������ml
		getYamlFile();
		log = Logger.getLogger(this.getClass());
	}

	public boolean openMenus(String menutree) {
		String menuPath[] = menutree.split("-");
		int treelevel = menuPath.length;
		log.debug( "�򿪲˵���" + menutree);
		try {
			WebElement lastmenu = findMenusWebElement(
					menuPath[menuPath.length - 1], "sub");
			// �˵��ڵ����ҵ�����ֱ�ӷ���true
			if (lastmenu != null) {
				return true;
			} else {
				// ���û�ҵ��˵��ڵ� ����Ҫ�����˵�·�� �𼶴�
				for (int i = 0; i < menuPath.length; i++) {
					String type = "node";
					if (i == menuPath.length - 1) {
						type = "sub";
					}
					lastmenu = findMenusWebElement(menuPath[i], type);
				}
			}
			// ���Ӷ��� �жϲ˵��Ƿ��Ѿ��� �������û������ ��ʱ����
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @param type
	 * @category: Ҷ�ӽڵ� ��Ҫ˫�� ����ֻҪ��������
	 * 
	 * */
	private WebElement findMenusWebElement(String menename, String type) {
		// 1.���Ҳ˵� Ŀ¼��
		new WebDriverWait(driver, waittime)
				.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
//						System.err.println("���Ҳ˵�Ŀ¼��ing");
						log.debug("���Ҳ˵�Ŀ¼��ing");
						List<WebElement> menus = driver.findElements(By
								.xpath("//div[@id='menuMain-body']//span"));
						if (menus.size() == 0) {
							return false;
						}
						return true;
					}
				});
		// �������в˵�Ԫ�� Ȼ����б��� ���ΪҶ�ӽڵ���˫�� �����˵�Ԫ�ص���
		List<WebElement> menus = driver.findElements(By
				.xpath("//div[@id='menuMain-body']//span"));
		for (WebElement e : menus) {
			if (e.getText().equals(menename)) {
				if (type == "sub") {// Ҷ�ӽڵ� �������ɴ�ҳ��
					new Actions(driver).click(e).perform();
				} else { // �˵��ڵ� ��Ҫ˫�����¼��˵�
					new Actions(driver).doubleClick(e).perform();
				}
				return e;
			}
		}
		return null;
	}

	public boolean closePage(String tabname) {
		boolean result = true;
		try {
			// �������еķ�ҳԪ��
			List<WebElement> tabs = driver.findElements(By
					.xpath("//div[@id='tabbar-1014-innerCt']/div/div"));
			for (int i = 0; i < tabs.size(); i++) {
				boolean isCurrentab = false;
				// System.err.println(tabs.size());
				WebElement tab = tabs.get(i);
				List<WebElement> tabspans = tab
						.findElements(By
								.xpath("./div/a/span[@class='x-tab-inner x-tab-inner-center']"));
				// ������ҳԪ���µ�����spanԪ�� �ж����Ƿ��ǵ�ǰ��ҳ �������Ϊtrue
				for (int j = 0; j < tabspans.size(); j++) {
					if (tabspans.get(j).getText().equals(tabname)) {
						isCurrentab = true;
					}
				}
				// ����ǵ�ǰ��ҳ�� �����ҵ��رհ�ť ����رհ�ť
				if (isCurrentab) {
					WebElement closeBtn = tab.findElement(By
							.xpath("./a[@class='x-tab-close-btn']"));
					closeBtn.click();
				}
			}
			// ���ԣ� ҳ���Ƿ�ر�
			List<WebElement> asserts = driver
					.findElements(By
							.xpath("//div/a/span[@class='x-tab-inner x-tab-inner-center']"));
			for (int k = 0; k < asserts.size(); k++) {
				if (asserts.get(k).getText().equals(tabname)) {
					result = false;
					// ҳ��û�йرճɹ�����false
					return false;
				}
			}
			// ��������ر�����Ϊ��
			result = true;
		} catch (Exception e) {
			// ����ʧ��
			return false;
		}
		return result;
	}

	/***
	 * @author qlj
	 * @param i
	 *            �ӳٺ�����
	 * @since 0426
	 * @exception ��
	 * **/
	public static void sleep(int i) {
		try {
			System.err.println("��ʱ" + i);			
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ҳ��Ԫ��ͳһ������
	 * 
	 * **/
	@SuppressWarnings("unchecked")
	private void getYamlFile() {
		File file = new File("locator/homePage.yaml");
		// System.err.println(file.getAbsolutePath());
		try {
			ml = Yaml.loadType(file, HashMap.class);
		} catch (FileNotFoundException e) {
//			System.err.println("��ȡyaml�ļ�����");
			log.error("��ȡyaml�ļ�����");
			e.printStackTrace();
		}
		// System.err.println(ml);
	}

	private By getBy(String type, String value) {
		By by = null;
//		System.err.println("getby" + type + value);
		log.debug("getby()  type=" + type  +",value="+ value);
		if (type.equals("id")) {
			by = By.id(value);
		}
		if (type.equals("name")) {
			by = By.name(value);
		}
		if (type.equals("className")) {
			by = By.className(value);
		}
		if (type.equals("xpath")) {
			by = By.xpath(value);
		}
		if (type.equals("css")) {
			by = By.cssSelector(value);
		}
		if (type.equals("linkText")) {
			by = By.linkText(value);
		}
//		System.err.println(by);
		log.debug(by);
		return by;
	}

	// ��ȡԪ��
	public WebElement getElement(String key) {
		return getLocator(key, null, true);
	}

	// ��ȡԪ��2 �滻yaml�ļ�valueֵ
	public WebElement getElement(String key, String[] ss) {
		return getLocator(key, ss, true);
	}

	// �ж�Ԫ����ʧ
	public WebElement getElementNoWait(String key) {
		// String key, String[] replace, boolean iswait
		// flase ��������ʱ��ȡ �����ж�Ԫ���Ƿ���� 
		return getLocator(key, null, false);
	}

	// �ж�Ԫ����ʧ2 �滻yaml�ļ�valueֵ
	public WebElement getElementNoWait(String key, String[] ss) {
		return getLocator(key, ss, false);
	}

	private String replaceLocator(String value, String[] ss) {
		String result = null;
		for (String s : ss) {
			result = value.replaceFirst("%s", s);
		}
		return result;
	}

	/**
	 * ���ӻ�ȡԪ���б��� ����ArrayList<WebElement>
	 * */
	// ��ȡ����ʾ��Ԫ��
	public List<WebElement> getDisplayedElements(String key) {
		return getLocators(key, true,true);
	}
	// ��ȡ����Ԫ��
	public List<WebElement> getAllElements(String key) {
		// �ȴ�Ԫ��true������ȫ����¼false
		return getLocators(key, true,false);
	}

	private List<WebElement> getLocators(final String key, boolean iswait,boolean isReturnDisplayed) { 
		String type = ml.get(key).get("type");
		String value = ml.get(key).get("value");
		final By by = getBy(type, value);
		// �ж��Ƿ�Ҫ�ȴ���ȡ �������ȴ�n���ȡ�б�
		List<WebElement> webelements = null;
		if (iswait) {
			  webelements = new WebDriverWait(driver,Config.waittime)
					.until(new ExpectedCondition<List<WebElement>>() {
						public List<WebElement> apply(WebDriver driver) {
//							System.err.println("����Ԫ��:"+key);
							log.debug("����Ԫ��:"+key);
							List<WebElement> webelementstmp =
									driver.findElements(by);
							if (webelementstmp== null || webelementstmp.size()==0  ) {
								return null;						
							}
							return webelementstmp;
						}
					});
			
		}else {
			webelements= driver.findElements(by);
		}
		//���true�򷵻�����ʾ������Ԫ�ؼ�¼
		List<WebElement>  unDisplayedElements = new ArrayList<WebElement>();
		log.debug("webelements:"+webelements.size());
		if (isReturnDisplayed) {
			for (WebElement e:  webelements ) { 
				//���Ԫ��δ��ʾ ��ɾ��
				if (!e.isDisplayed() ) {
					unDisplayedElements.add(e);
				} 
			}
			webelements.removeAll(unDisplayedElements);
		}
//		System.err.println("webelements:"+webelements.size());
		log.debug("webelements:"+webelements.size());
		return webelements;
	}

	/**
	 * ��λԪ�أ�ͨ��key�ҵ������ļ��еĲ�����ͨ��replace�滻valueֵ,�����Ƿ�ȴ��ж�
	 * 
	 * @param key
	 *            ͨ��key�ҵ�type��value
	 * @param replace
	 *            �滻value�е�%s ֧���滻���
	 * @param iswait
	 *            �ж��Ƿ��¼ ���ʱ�������ж�Ԫ���Ƿ����
	 * 
	 * */
	private WebElement getLocator(String key, String[] replace, boolean iswait) {
		WebElement element = null;
		if (!ml.containsKey(key)) {
//			System.err.println("yaml�ļ�������key:" + key); 
			log.warn("yaml�ļ�������key:" + key);
			return null;
		}
		// ͨ���ؼ��ֻ�ȡ��Ԫ�ض�λ����type��ֵvalue Ȼ���ȡ��by
		String type = ml.get(key).get("type");
		String value = null;
		if (replace == null) {
			value = ml.get(key).get("value");
		} else {
			value = replaceLocator("%s", replace);
		}
		By by = getBy(type, value);
		// �ȴ�Ԫ��10 ��Ԫ������ʾ
		if (iswait) {
			element = waitforElement(by);
			if (!waitElementToBeDisplayed(element)) {
				return null;
			}
			// �����ȴ��жϣ�ֻҪ�ܶ�λ��Ԫ�ؼ���
		} else {
			try {
				element = driver.findElement(by);
			} catch (Exception e) {
				element = null;
			}
		}
		return element;
	}

	// �ȴ�Ԫ���Ƿ�λ��
	private WebElement waitforElement(final By by) {
		WebElement e = new WebDriverWait(driver, Config.waittime)
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(by);
					}
				});
		return e;
	}

	// �ȴ�Ԫ���Ƿ���ʾ
	private boolean waitElementToBeDisplayed(final WebElement e) {
		boolean wait = false;
		if (e == null) {
			return wait;
		}
		try {
			wait = new WebDriverWait(driver, Config.waittime)
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver dirver) {
//							System.err.println("waitElementToBeDisplayed ������");
							log.debug("waitElementToBeDisplayed ������");
							return e.isDisplayed();
						}
					});
		} catch (Exception e2) {
		}
		return wait;
	}

	// �ж�Ԫ���Ƿ���ʧ
	private boolean waitElementToBeNonDisplayed(final WebElement element) {
		boolean isdisappear = false;
		if (element == null) {
			return isdisappear;
		}
		try {
			isdisappear = new WebDriverWait(driver, Config.waittime)
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver driver) {
							return !element.isDisplayed();
						}
					});
		} catch (Exception e) {
//			System.err.println("10s��Ԫ��δ����");
			log.error("10s��Ԫ��δ����");
		}
		return isdisappear;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		// ProfilesIni allProfiles = new ProfilesIni();
		// FirefoxProfile profile2 = allProfiles.getProfile("test");
		// FirefoxProfile profile = new FirefoxProfile(new File("d:\\test"))
		// WebDriver driver = new FirefoxDriver(profile2);

//		WebDriver driver = new FirefoxDriver();
//		driver.get("http://115.29.198.101:8011/index.htm");
		// System.err.println(driver);
//		HomePage l = new HomePage(driver);
		// driver.get("http://guyi2013.vicp.cc:8088/index.htm");
		// driver.get("http://115.29.198.101:8011/index.htm");
		// Boolean ispass = l.login("test", "test",
		// "http://guyi2013.vicp.cc:8011/");
		// Boolean ispass = l.login("test", "test",
		// "http://115.29.198.101:8011/index.htm");
		// Boolean ispass = l.login("test", "test",
		// "http://guyi2013.vicp.cc:8088/index.htm");

		// System.err.println(ispass);
		 BasePage b = new BasePage(null);
		 b.log.info("asdf");
		// b.openMenus("��������-�ն˹���");
		// b.openMenus("��Ϣ����-��Ϣ����");
		// b.openMenus("��Ϣ����-�˼۹���");
		// b.openMenus("��Ϣ����-�ͻ�����");
		// b.openMenus("��������-��Ϣ����");
		// b.openMenus("��������-�ն˹���");
		// b.openMenus("��������-��������");
		// b.openMenus("��������-��ʻԱ����");
		// b.openMenus("��������-�������");
		// boolean re= b.closePage("��Ϣ����");
		// boolean re1= b.closePage("�ͻ�����");
		// boolean re2= b.closePage("��Ϣ����");
		// boolean re3= b.closePage("��Ϣ����");
		// boolean re4= b.closePage("�������");
		// boolean re5= b.closePage("����̨");
		// System.err.println("end"+re +re1+re2+re4+re5);

		// 0505Ԫ��ͳһ��
		// l.getYamlFile();
		sleep(3000);

//		System.err.println(l.getElement("username"));
//		WebElement username = l.getElement("username");
//		username.sendKeys("test");
		

	}

}
