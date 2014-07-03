package com.guyi.BaseConfig;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;

public class WarnType {
	static Set<String> warnitems;

	public WarnType() {
		warnitems = new HashSet<String>();
		String s = "所有-紧急报瞥触动报警开关后触发-超速报警-疲劳驾驶-危险预警-GNSS模块发生故障-GNSS天线未接或被剪断-GNSS天线短路-终端主电源欠压-终端主电源掉电-终端LCD或显示器故障-TTS模块故障-摄像头故障-道路运输证IC卡模块故障-超速预警-疲劳驾驶预警-当天累计驾驶超时-超时停车-进出区域-进出路线-路段行驶时间不足/过长-路线偏离报警-车辆VSS故障-车辆油量异常-车辆被盗(通过车辆防盗器)-车辆非法点火-车辆非法位移-碰撞预警-侧翻预警-油量告警-温度告警";
		String items[] = s.split("-");
//		System.err.println(items.length);
		for (int i = 0; i < items.length; i++) {
			warnitems.add(items[i]);
		}
	}

	// 判断集合是否相等
	public static boolean checkWarntype(List<WebElement> listWarntypes) {
		System.err.println("checking:");
		if (listWarntypes == null) {
			return false;
		}
  
		Set<String> SetWarnMinusResult = new HashSet();
		Set<String> SetCurentWebWarntypes = new HashSet();
		for (WebElement warntype :listWarntypes) {
			SetCurentWebWarntypes.add( warntype.getText()); 
		}
		SetCurentWebWarntypes.remove("");
		if (SetCurentWebWarntypes.size() !=  warnitems.size()) {
			return false;
		} 
		//1.先复制所有报警类型  
		SetWarnMinusResult.addAll(warnitems);
		//取交集 
		SetWarnMinusResult.retainAll(SetCurentWebWarntypes); 
		if (SetWarnMinusResult.size() == 0  ) {
			return true;
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// for (Iterator iterator = w.warnitems.iterator(); iterator.hasNext();)
		// {
		// System.err.println(iterator.next());
		// }
		Set<String> s1 = new HashSet<String>();
		Set<String> warnitems2 = new HashSet<String>();		
		String str = "所有-紧急报瞥触动报警开关后触发-超速报警-疲劳驾驶-危险预警-GNSS模块发生故障-GNSS天线未接或被剪断-GNSS天线短路-终端主电源欠压-终端主电源掉电-终端LCD或显示器故障-TTS模块故障-摄像头故障-道路运输证IC卡模块故障-超速预警-疲劳驾驶预警-当天累计驾驶超时-超时停车-进出区域-进出路线-路段行驶时间不足/过长-路线偏离报警-车辆VSS故障-车辆油量异常-车辆被盗(通过车辆防盗器)-车辆非法点火-车辆非法位移-碰撞预警-侧翻预警-油量告警-温度告警";
		warnitems2.add("所有"); 
		String items[] = str.split("-");
		for (int i = 0; i < items.length; i++) {
			warnitems2.add(items[i]);
		}
		System.err.println("test:"+warnitems2);
//		System.err.println(new WarnType().checkWarntype(items));
		
	    HashSet<Integer> setA = new HashSet<Integer>();
        setA.add(1);
        setA.add(3);
        setA.add(5);
        HashSet<Integer> setB = new HashSet<Integer>();
        setB.add(1);
        setB.add(6);
        HashSet<Integer> setC = new HashSet<Integer>();
        setC.clear();
        setC.addAll(setA);
        setC.retainAll(setB);
        System.out.println("setC=" + setC);
	}

}
