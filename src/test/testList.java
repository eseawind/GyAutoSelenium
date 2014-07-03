package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class testList {

	public testList() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> s = new ArrayList<String>();
		s.add("a1");
		s.add("a2");
		s.add("a3");
		s.add("a4");
//		s.remove(1);  
//		for (int i = 0; i < s.size(); i++) {
//			System.err.println(String.valueOf(i)+s.get(i));
//			if (i==1) {
//				s.remove(1);
//			}
//			System.err.println(String.valueOf(i)+s.get(i)); 
//		}
		List<String> stmp =  new ArrayList<String>();
		for (String aa : s) {
			if (aa.equals("a2")) {
				 stmp.add(aa); 
			}
			if (aa.equals("a5")) {
				 stmp.add(aa); 
			}
			System.err.println(aa); 
		}
		s.removeAll(stmp);
		System.err.println(s);
	}

}
