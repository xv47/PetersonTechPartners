package com.peterson.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.peterson.servlets.login.Validate;
import com.peterson.util.MySQL;

public class UploadFileControl{
	protected static String host = "jdbc:mysql://localhost/userdb";
	protected static String user = "edward";
	protected static String pass = "";
	private static Connection con;
	private static Logger logger = Logger.getLogger(UploadFileControl.class);

	public static void init() {
		BasicConfigurator.configure();
	}
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
	public static String nameHash(String name) {
		String s1 = Validate.encrypt(name);
		String s2 = s1.substring(0, 10);
		return s2;
	}

	public static String imgRename(String hash, String name) {
		int index = name.lastIndexOf('.');
		String ext = new String();
		String newName = new String();
		
		// Make sure there is anything after the period
		if (index > 0) {
			if (name.length() > index) {
				ext = name.substring(index, name.length());
			}
		}
		
		newName = hash + ext;
		return newName;
	}

	public static boolean addEmployeeImg(String email, String localPath) {
		boolean b = false;
		try {
			connectDB();

			PreparedStatement ps = con
					.prepareStatement("UPDATE userdb.employee SET img=? WHERE email=?");
			ps.setString(1, localPath);
			ps.setString(2, email);
			ps.execute();

			b = true;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;
	}
}
