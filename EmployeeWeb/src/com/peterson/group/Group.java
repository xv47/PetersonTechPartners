package com.peterson.group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.peterson.servlets.login.Validate;

public class Group {
	protected static String host = "jdbc:mysql://localhost/userdb";
	protected static String user = "edward";
	protected static String pass = "";
	private static String table = "group";
	private static Connection con;
	private static Logger logger = Logger.getLogger(Validate.class);

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

	public static boolean addGroup(String name, int mID) {
		boolean b = false;
		try {
			connectDB();

			PreparedStatement ps = con.prepareStatement("INSERT INTO userdb."
					+ table + "(name,mID) VALUES(?,?)");
			ps.setString(1, name);
			ps.setLong(2, mID);

			ps.execute();
			b = true;

		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;
	}
	
	public static boolean doesGroupExist(String name) {
		boolean b = false;
		try {
			connectDB();

			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM userdb.group WHERE name=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				b = true;
			} else {
				b = false;
			}

		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;
	}

	public static int getGroupID(String name) {
		int id = 0;
		try {
			connectDB();

			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM userdb.group WHERE name=?");
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id = (int) rs.getLong("id");
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return id;
	}

	public static JSONObject getGroup(int ID) {
		int id = 0;
		JSONObject json = new JSONObject();
		try {
			connectDB();

			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM userdb.group WHERE id=?");
			ps.setLong(1, ID);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				json.put("name", rs.getString("name"));
				json.put("mID", Validate.getEmpName((int) rs.getLong("mID")));
			} else {
				logger.info("No records found for group");
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return json;
	}

	public static boolean joinGroup(String name, String email) {

		boolean b = false;
		int ID = getGroupID(name);

		try {
			connectDB();

			PreparedStatement ps = con
					.prepareStatement("UPDATE userdb.employee SET gID=? WHERE email=?");
			ps.setLong(1, ID);
			ps.setString(2, email);

			ps.execute();
			b = true;

		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;
	}
	public static boolean joinGroup(int id, String email) {

		boolean b = false;
		

		try {
			connectDB();

			PreparedStatement ps = con
					.prepareStatement("UPDATE userdb.employee SET gID=? WHERE email=?");
			ps.setLong(1, id);
			ps.setString(2, email);

			ps.execute();
			b = true;

		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;
	}
	public static String getGroup(String empEmail) {
		String out = new String();
		int gID = 0;
		try {
			connectDB();

			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM userdb.employee WHERE email=?");
			ps.setString(1, empEmail);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				gID = (int) rs.getLong("gID");
				out = getGroup(gID).toJSONString();
			} else {
				logger.info("User not found");
			}

		} catch (Exception e) {
			logger.error(e.toString());
		}
		return out;
	}
	public static JSONArray getGroupMembers(int id){
		JSONArray jArray = new JSONArray();
		JSONObject emp = new JSONObject();
		
		try{
			connectDB();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM userdb.employee WHERE gID=?");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				emp.put("fname", rs.getString("fname"));
				emp.put("lname", rs.getString("lname"));
				emp.put("id", rs.getInt("id"));
				emp.put("email", rs.getString("email"));
				jArray.add(emp);
				emp = new JSONObject();
			}
			
		}catch(Exception e){
			logger.error(e.toString());
		}
		return jArray;
	}
	public static boolean removeGroupMember(String email){
		boolean b = false;
		try{
			connectDB();
			PreparedStatement ps = con.prepareStatement("UPDATE userdb.employee SET gID=? WHERE email=?");
			ps.setInt(1,0);
			ps.setString(2, email);
			ps.execute();
			b=true;
			
		}catch(Exception e){
			logger.error(e.toString());
		}
		return b;
	}
	public static boolean removeGroupMember(int id){
		boolean b = false;
		try{
			connectDB();
			PreparedStatement ps = con.prepareStatement("UPDATE userdb.employee SET gID=? WHERE id=?");
			ps.setInt(1,0);
			ps.setInt(2, id);
			ps.execute();
			b=true;
			
		}catch(Exception e){
			logger.error(e.toString());
		}
		return b;
	}
}
