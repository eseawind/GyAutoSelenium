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
 * 数据库校验
 * */
public  class CheckDataBase {
    //设定数据库驱动，数据库连接地址、端口、名称，用户名，密码  
    static String driverName="oracle.jdbc.driver.OracleDriver";  
    static String url="jdbc:oracle:thin:@192.168.1.185:1523:lenglian";  //test为数据库名称，1521为连接数据库的默认端口  
    static String user="sa";   //sa为用户名  
    static String password="sa";  //sa为密码  
    static PreparedStatement pstmt = null;  
    static ResultSet rs = null;  
    //数据库连接对象  
    static  Connection conn = null; 
    //关闭数据
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
    //连接数据库
    public static void connDataBase(String sql){
		try {
			Class.forName(driverName);
	        //获取数据库连接  
	        conn = DriverManager.getConnection(url, user, password); 
//	        System.err.println(conn);
	        //创建该连接下的PreparedStatement对象  
	        pstmt = conn.prepareStatement(sql);  
//	        System.err.println(pstmt);
	        //执行查询语句，将数据保存到ResultSet对象中  
	        rs = pstmt.executeQuery();
//	        System.err.println(rs);
		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} catch (SQLException e) { 
			System.err.println("CheckDataBase数据库连接异常");
			e.printStackTrace();
		}

    }
    public static int checkNum(String sql){
    	int result =0;
        //反射Oracle数据库驱动程序类  
        try {
        	connDataBase(  sql);
            //将指针移到下一行，判断rs中是否有数据  
            if(rs.next()){  
                //输出查询结果   从1开始
//                System.err.println("总记录数："+rs.getString(1));  
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
//		System.err.println("总记录："+i);
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
