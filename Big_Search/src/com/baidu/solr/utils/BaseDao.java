package com.baidu.solr.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDao {
	private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private static final String USERNAME = "chunguang";
	private static final String PASSWORD = "chunguang";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:GCG";
	
	
	public BaseDao() {
		
	}


	/**
	 * 注册驱动
	 */
	static{
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			System.out.println("注册驱动失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	public Connection getConncection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("数据库连接失败");
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 执行增、删、改语句
	 * @param sql sql语句
	 * @param params 参数列表
	 * @return 受影响的行数
	 */
	public int executeUpdate(String sql,Object...params){
		Connection conn = this.getConncection();
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			//参数赋值
			if(params!= null && params.length != 0){
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i+1, params[i]);
				}
			}
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(null, pstmt, conn);
		}
		return result;
		
		
	}
	
	/**
	 * 获取结果集
	 * @param sql
	 * @param obj
	 * @return
	 */
	public  ResultSet getResultSet(String sql,Object...params){
		Connection conn = this.getConncection();
		ResultSet rs=null;
		PreparedStatement pstmt = null;
		try {
			 pstmt=conn.prepareStatement(sql);
			if(params!= null && params.length != 0){
				for (int i = 0; i < params.length; i++) {
					
					pstmt.setObject(i+1, params[i]);
					
				}
			}
			rs=pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
		
	}

	
	/**
	 * 释放资源
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public void closeAll(ResultSet rs, Statement stmt, Connection conn){
		try {
			if(rs != null){rs.close(); rs = null;}
			if(stmt != null){stmt.close(); stmt = null;}
			if(conn != null){conn.close(); conn = null;}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
