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
	// 设置默认等待时间
	int waittime;
	// 所有元素
	HashMap<String, HashMap<String, String>> ml;

	public Logger log;
	public BasePage(WebDriver d) {
		// TODO Auto-generated constructor stub
		driver = d;
		waittime = 20;
		// 读取yaml文件 并把值赋给变量ml
		getYamlFile();
		log = Logger.getLogger(this.getClass());
	}

	public boolean openMenus(String menutree) {
		String menuPath[] = menutree.split("-");
		int treelevel = menuPath.length;
		log.debug( "打开菜单：" + menutree);
		try {
			WebElement lastmenu = findMenusWebElement(
					menuPath[menuPath.length - 1], "sub");
			// 菜单节点已找到，则直接返回true
			if (lastmenu != null) {
				return true;
			} else {
				// 如果没找到菜单节点 则需要遍历菜单路径 逐级打开
				for (int i = 0; i < menuPath.length; i++) {
					String type = "node";
					if (i == menuPath.length - 1) {
						type = "sub";
					}
					lastmenu = findMenusWebElement(menuPath[i], type);
				}
			}
			// 增加断言 判断菜单是否已经打开 这个基本没有问题 暂时不加
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @param type
	 * @category: 叶子节点 需要双击 其他只要单击即可
	 * 
	 * */
	private WebElement findMenusWebElement(String menename, String type) {
		// 1.查找菜单 目录树
		new WebDriverWait(driver, waittime)
				.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
//						System.err.println("查找菜单目录树ing");
						log.debug("查找菜单目录树ing");
						List<WebElement> menus = driver.findElements(By
								.xpath("//div[@id='menuMain-body']//span"));
						if (menus.size() == 0) {
							return false;
						}
						return true;
					}
				});
		// 查找所有菜单元素 然后进行遍历 如果为叶子节点则双击 其他菜单元素单击
		List<WebElement> menus = driver.findElements(By
				.xpath("//div[@id='menuMain-body']//span"));
		for (WebElement e : menus) {
			if (e.getText().equals(menename)) {
				if (type == "sub") {// 叶子节点 单击即可打开页面
					new Actions(driver).click(e).perform();
				} else { // 菜单节点 需要双击打开下级菜单
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
			// 查找所有的分页元素
			List<WebElement> tabs = driver.findElements(By
					.xpath("//div[@id='tabbar-1014-innerCt']/div/div"));
			for (int i = 0; i < tabs.size(); i++) {
				boolean isCurrentab = false;
				// System.err.println(tabs.size());
				WebElement tab = tabs.get(i);
				List<WebElement> tabspans = tab
						.findElements(By
								.xpath("./div/a/span[@class='x-tab-inner x-tab-inner-center']"));
				// 遍历分页元素下的所有span元素 判断其是否是当前分页 如果是则为true
				for (int j = 0; j < tabspans.size(); j++) {
					if (tabspans.get(j).getText().equals(tabname)) {
						isCurrentab = true;
					}
				}
				// 如果是当前分页则 重新找到关闭按钮 点击关闭按钮
				if (isCurrentab) {
					WebElement closeBtn = tab.findElement(By
							.xpath("./a[@class='x-tab-close-btn']"));
					closeBtn.click();
				}
			}
			// 断言： 页面是否关闭
			List<WebElement> asserts = driver
					.findElements(By
							.xpath("//div/a/span[@class='x-tab-inner x-tab-inner-center']"));
			for (int k = 0; k < asserts.size(); k++) {
				if (asserts.get(k).getText().equals(tabname)) {
					result = false;
					// 页面没有关闭成功返回false
					return false;
				}
			}
			// 如果正常关闭则结果为真
			result = true;
		} catch (Exception e) {
			// 返回失败
			return false;
		}
		return result;
	}

	/***
	 * @author qlj
	 * @param i
	 *            延迟毫秒数
	 * @since 0426
	 * @exception 无
	 * **/
	public static void sleep(int i) {
		try {
			System.err.println("延时" + i);			
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 页面元素统一化管理
	 * 
	 * **/
	@SuppressWarnings("unchecked")
	private void getYamlFile() {
		File file = new File("locator/homePage.yaml");
		// System.err.println(file.getAbsolutePath());
		try {
			ml = Yaml.loadType(file, HashMap.class);
		} catch (FileNotFoundException e) {
//			System.err.println("读取yaml文件出错");
			log.error("读取yaml文件出错");
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

	// 获取元素
	public WebElement getElement(String key) {
		return getLocator(key, null, true);
	}

	// 获取元素2 替换yaml文件value值
	public WebElement getElement(String key, String[] ss) {
		return getLocator(key, ss, true);
	}

	// 判断元素消失
	public WebElement getElementNoWait(String key) {
		// String key, String[] replace, boolean iswait
		// flase 代表不做延时获取 而是判断元素是否存在 
		return getLocator(key, null, false);
	}

	// 判断元素消失2 替换yaml文件value值
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
	 * 增加获取元素列表功能 返回ArrayList<WebElement>
	 * */
	// 获取有显示的元素
	public List<WebElement> getDisplayedElements(String key) {
		return getLocators(key, true,true);
	}
	// 获取所有元素
	public List<WebElement> getAllElements(String key) {
		// 等待元素true并返回全部记录false
		return getLocators(key, true,false);
	}

	private List<WebElement> getLocators(final String key, boolean iswait,boolean isReturnDisplayed) { 
		String type = ml.get(key).get("type");
		String value = ml.get(key).get("value");
		final By by = getBy(type, value);
		// 判断是否要等待获取 如果是则等待n秒获取列表
		List<WebElement> webelements = null;
		if (iswait) {
			  webelements = new WebDriverWait(driver,Config.waittime)
					.until(new ExpectedCondition<List<WebElement>>() {
						public List<WebElement> apply(WebDriver driver) {
//							System.err.println("查找元素:"+key);
							log.debug("查找元素:"+key);
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
		//如果true则返回有显示出来的元素记录
		List<WebElement>  unDisplayedElements = new ArrayList<WebElement>();
		log.debug("webelements:"+webelements.size());
		if (isReturnDisplayed) {
			for (WebElement e:  webelements ) { 
				//如果元素未显示 则删除
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
	 * 定位元素：通过key找到配置文件中的参数，通过replace替换value值,增加是否等待判断
	 * 
	 * @param key
	 *            通过key找到type和value
	 * @param replace
	 *            替换value中的%s 支持替换多个
	 * @param iswait
	 *            判断是否登录 否的时候用于判断元素是否存在
	 * 
	 * */
	private WebElement getLocator(String key, String[] replace, boolean iswait) {
		WebElement element = null;
		if (!ml.containsKey(key)) {
//			System.err.println("yaml文件不存在key:" + key); 
			log.warn("yaml文件不存在key:" + key);
			return null;
		}
		// 通过关键字获取到元素定位类型type和值value 然后获取到by
		String type = ml.get(key).get("type");
		String value = null;
		if (replace == null) {
			value = ml.get(key).get("value");
		} else {
			value = replaceLocator("%s", replace);
		}
		By by = getBy(type, value);
		// 等待元素10 且元素已显示
		if (iswait) {
			element = waitforElement(by);
			if (!waitElementToBeDisplayed(element)) {
				return null;
			}
			// 不做等待判断，只要能定位到元素即可
		} else {
			try {
				element = driver.findElement(by);
			} catch (Exception e) {
				element = null;
			}
		}
		return element;
	}

	// 等待元素是否定位到
	private WebElement waitforElement(final By by) {
		WebElement e = new WebDriverWait(driver, Config.waittime)
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(by);
					}
				});
		return e;
	}

	// 等待元素是否显示
	private boolean waitElementToBeDisplayed(final WebElement e) {
		boolean wait = false;
		if (e == null) {
			return wait;
		}
		try {
			wait = new WebDriverWait(driver, Config.waittime)
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver dirver) {
//							System.err.println("waitElementToBeDisplayed 。。。");
							log.debug("waitElementToBeDisplayed 。。。");
							return e.isDisplayed();
						}
					});
		} catch (Exception e2) {
		}
		return wait;
	}

	// 判断元素是否消失
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
//			System.err.println("10s内元素未隐藏");
			log.error("10s内元素未隐藏");
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
		// b.openMenus("基础设置-终端管理");
		// b.openMenus("信息服务-信息推送");
		// b.openMenus("信息服务-运价管理");
		// b.openMenus("信息服务-客户管理");
		// b.openMenus("基础设置-信息管理");
		// b.openMenus("基础设置-终端管理");
		// b.openMenus("基础设置-车辆管理");
		// b.openMenus("基础设置-驾驶员管理");
		// b.openMenus("基础设置-问题管理");
		// boolean re= b.closePage("信息推送");
		// boolean re1= b.closePage("客户管理");
		// boolean re2= b.closePage("信息推送");
		// boolean re3= b.closePage("信息管理");
		// boolean re4= b.closePage("问题管理");
		// boolean re5= b.closePage("工作台");
		// System.err.println("end"+re +re1+re2+re4+re5);

		// 0505元素统一化
		// l.getYamlFile();
		sleep(3000);

//		System.err.println(l.getElement("username"));
//		WebElement username = l.getElement("username");
//		username.sendKeys("test");
		

	}

}
