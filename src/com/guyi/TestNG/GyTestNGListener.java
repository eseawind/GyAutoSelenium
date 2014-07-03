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
	// ��������ʧ�����ͼ
	@Override
	public void onTestFailure(ITestResult tr) { 
		super.onTestFailure(tr);
		System.err.println("��ʼfailure-----");
		takeScreenShot(tr);
	} 
	@Override
	public void onTestSkipped(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestSkipped(tr);
		System.err.println("��ʼskipped-----");
		takeScreenShot(tr);
	}

	
	
	@Override
	public void onTestSuccess(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestSuccess(tr);	
		takeScreenShot(tr);
		
	}
	/**
	 * �Զ���ͼ������ͼƬ�������Լ�html����ļ���
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
        //����ʵ�ְ�ͼƬ����ֱ�����������ļ��У�ͨ���ʼ����ͽ�������ֱ����ʾͼƬ
		Reporter.log("<img src=\"../" + fileName +   "\" width=\"270\" height=\"129\">");
		
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
