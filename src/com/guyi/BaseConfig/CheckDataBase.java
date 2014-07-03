package com.guyi.BaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Iterables;

/**
 * ���ݿ�У��
 * */
public  class CheckDataBase {
    //�趨���ݿ����������ݿ����ӵ�ַ���˿ڡ����ƣ��û���������  
    static String driverName="oracle.jdbc.driver.OracleDriver";  
    static String url="jdbc:oracle:thin:@192.168.1.185:1523:lenglian";  //testΪ���ݿ����ƣ�1521Ϊ�������ݿ��Ĭ�϶˿�  
    static String user="sa";   //saΪ�û���  
    static String password="sa";  //saΪ����  
    static PreparedStatement pstmt = null;  
    static ResultSet rs = null;  
    //���ݿ����Ӷ���  
    static  Connection conn = null; 
    //�ر�����
    public static void close() {
        try {
          if (rs != null) {
            rs.close();
          }
          if (pstmt != null) {
        	  pstmt.close();
          }
          if (conn != null) {
              conn.close();
            }
        }
        catch (Exception e) {
          e.printStackTrace(System.err);
        }   
      }
    //�������ݿ�
    public static void connDataBase(String sql){
		try {
			Class.forName(driverName);
	        //��ȡ���ݿ�����  
	        conn = DriverManager.getConnection(url, user, password); 
//	        System.err.println(conn);
	        //�����������µ�PreparedStatement����  
	        pstmt = conn.prepareStatement(sql);  
//	        System.err.println(pstmt);
	        //ִ�в�ѯ��䣬�����ݱ��浽ResultSet������  
	        rs = pstmt.executeQuery();
//	        System.err.println(rs);
		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} catch (SQLException e) { 
			System.err.println("CheckDataBase���ݿ������쳣");
			e.printStackTrace();
		}

    }
    public static int checkNum(String sql){
    	int result =0;
        //����Oracle���ݿ�����������  
        try {
        	connDataBase(  sql);
            //��ָ���Ƶ���һ�У��ж�rs���Ƿ�������  
            if(rs.next()){  
                //�����ѯ���   ��1��ʼ
//                System.err.println("�ܼ�¼����"+rs.getString(1));  
                result = Integer.valueOf(rs.getString(1));
            } 
		}   catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
    	return result; 
    }
 
    public static void checkoneRecord(String sql,HashMap<String,String> map){
    	connDataBase(sql); 
    	try {
			if (rs.next()) {
				Iterator<Entry<String, String>> itr = map.entrySet().iterator();
				while (itr.hasNext()) {
					Map.Entry<String,String> entry=(Map.Entry<String,String>) itr.next();
//					System.err.println("test---"+entry.getKey());
					try {
//						System.err.println(entry.getKey());
						String tmp =rs.getString(entry.getKey());
						entry.setValue(tmp);
					} catch (SQLException e) { 
						e.printStackTrace();
					}
				} 
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		close();
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sql= "select *  from tb_base_fee where fee_id =1241   ";
//		int i =CheckDataBase.checkNum(sql);
//		System.err.println("�ܼ�¼��"+i);
		HashMap map = new HashMap<String, String>();
		map.put("fee_id", null);
		map.put("start_site", null); 
		map.put("end_site", null);
		map.put("road_toll", null);
		map.put("kilo_number", null);
		CheckDataBase.checkoneRecord(sql, map);
		System.err.println(map);
	}

}
