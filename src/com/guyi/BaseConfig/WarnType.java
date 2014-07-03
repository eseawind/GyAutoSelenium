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
		String s = "����-������Ƴ�����������غ󴥷�-���ٱ���-ƣ�ͼ�ʻ-Σ��Ԥ��-GNSSģ�鷢������-GNSS����δ�ӻ򱻼���-GNSS���߶�·-�ն�����ԴǷѹ-�ն�����Դ����-�ն�LCD����ʾ������-TTSģ�����-����ͷ����-��·����֤IC��ģ�����-����Ԥ��-ƣ�ͼ�ʻԤ��-�����ۼƼ�ʻ��ʱ-��ʱͣ��-��������-����·��-·����ʻʱ�䲻��/����-·��ƫ�뱨��-����VSS����-���������쳣-��������(ͨ������������)-�����Ƿ����-�����Ƿ�λ��-��ײԤ��-�෭Ԥ��-�����澯-�¶ȸ澯";
		String items[] = s.split("-");
//		System.err.println(items.length);
		for (int i = 0; i < items.length; i++) {
			warnitems.add(items[i]);
		}
	}

	// �жϼ����Ƿ����
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
		//1.�ȸ������б�������  
		SetWarnMinusResult.addAll(warnitems);
		//ȡ���� 
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
		String str = "����-������Ƴ�����������غ󴥷�-���ٱ���-ƣ�ͼ�ʻ-Σ��Ԥ��-GNSSģ�鷢������-GNSS����δ�ӻ򱻼���-GNSS���߶�·-�ն�����ԴǷѹ-�ն�����Դ����-�ն�LCD����ʾ������-TTSģ�����-����ͷ����-��·����֤IC��ģ�����-����Ԥ��-ƣ�ͼ�ʻԤ��-�����ۼƼ�ʻ��ʱ-��ʱͣ��-��������-����·��-·����ʻʱ�䲻��/����-·��ƫ�뱨��-����VSS����-���������쳣-��������(ͨ������������)-�����Ƿ����-�����Ƿ�λ��-��ײԤ��-�෭Ԥ��-�����澯-�¶ȸ澯";
		warnitems2.add("����"); 
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
