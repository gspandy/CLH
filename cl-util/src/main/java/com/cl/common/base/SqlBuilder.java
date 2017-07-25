
package com.cl.common.base;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @Filename SqlBuilder.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 *
 */
public class SqlBuilder {
	public static void main(String[] args) {
		String dataBase = "clh";
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = java.sql.DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/" + dataBase, "root", "123456");
			
			// For SQLSEVER
			// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// Connection con = java.sql.DriverManager.getConnection(
			// "jdbc:sqlserver://192.168.0.33:1433;DatabaseName=www_sxdsw_net",
			// "xiaomayi",
			// "xiaomayi");
			DataBaseUtil dbu = new DataBaseUtil(con, null);
			dbu.makeSql();
			con.close();
			
		} catch (ClassNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
