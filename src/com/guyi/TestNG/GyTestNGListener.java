package com.guyi.TestNG;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
 

public class GyTestNGListener extends TestListenerAdapter {

	public GyTestNGListener() {

	}
	// 用例运行失败则截图
	@Override
	public void onTestFailure(ITestResult tr) { 
		super.onTestFailure(tr);
		System.err.println("开始failure-----");
		takeScreenShot(tr);
	} 
	@Override
	public void onTestSkipped(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestSkipped(tr);
		System.err.println("开始skipped-----");
		takeScreenShot(tr);
	}

	
	
	@Override
	public void onTestSuccess(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestSuccess(tr);	
		takeScreenShot(tr);
		
	}
	/**
	 * 自动截图，保存图片到本地以及html结果文件中
	 * 
	 * @param tr
	 */
	private void takeScreenShot(ITestResult tr) { 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
		String mDateTime = formatter.format(new Date());
//		String fileName = "f:\\"+ mDateTime + "_" + tr.getName()+".jpg";
		String fileName = "testgy\\"+mDateTime + "_" + tr.getName()+".jpg";
		System.err.println("fileName             ="+fileName);
//		String filePath = OrangeiOS.driver.getScreenshotAs(fileName);
		File destFile = new File(fileName);
		File filesrc = ((TakesScreenshot)(TestNGBase.driver )).getScreenshotAs(OutputType.FILE); 
		try {
			FileUtils.copyFile(filesrc, destFile);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		//
		Reporter.setCurrentTestResult(tr);
		Reporter.log(destFile.getName());
        //这里实现把图片链接直接输出到结果文件中，通过邮件发送结果则可以直接显示图片
		Reporter.log("<img src=\"../" + fileName +   "\" width=\"270\" height=\"129\">");
		
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
