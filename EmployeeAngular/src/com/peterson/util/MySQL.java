package com.peterson.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MySQL {
	protected static String host = "jdbc:mysql://localhost/userdb";
	protected static String user = "edward";
	protected static String pass = "";
	protected static String table = "employee";
	protected static Connection con;
	
	private static Logger logger = Logger.getLogger(MySQL.class);
	
	public static void connectDB() throws ClassNotFoundException, SQLException {

		// load MySQL driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// Connect to database
			con = DriverManager.getConnection(host, user, pass);
		} catch (SQLException e) {
			logger.info("Error connecting to database: " + e.toString());
			e.printStackTrace();
		}
	}
}
