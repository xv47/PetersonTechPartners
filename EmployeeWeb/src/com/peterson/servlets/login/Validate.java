package com.peterson.servlets.login;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.peterson.employee.Developer;
import com.peterson.employee.Employee;
import com.peterson.employee.Manager;
import com.peterson.employee.QA;

public class Validate {
	private static String host = "jdbc:mysql://localhost/userdb";
	private static String user = "edward";
	private static String pass = "";
	private static String table = "employee";
	private static Connection con;
	private static Logger logger = Logger.getLogger(Validate.class);

	public static void init() {
		BasicConfigurator.configure();
	}
	public static int getID(String email){
		int st = 0;

		try {
			connectDB();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM "
					+ table + " WHERE email=?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				st = rs.getInt("id");
			} else {
				logger.error("Record for " + email + " not found");	
				st = 0;
			}

		} catch (Exception e) {
			logger.error("Error getting user " + email + ": " + e.toString());
			e.printStackTrace();
		}

		return st;
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
	 
	public static boolean isActive(String email) {
		boolean st = false;

		try {
			connectDB();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM "
					+ table + " WHERE email=?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getInt("active") == 1) {

					logger.info(email + " is an active account");
					st = true;
				} else {
					logger.info(email + " is not an active account");
					st = false;
				}
			} else {

				logger.error("Record for " + email + " not found");
				st = false;
			}

		} catch (Exception e) {
			logger.error("Error getting user " + email + ": " + e.toString());
			e.printStackTrace();
		}

		return st;
	}
	public static boolean isAdmin(String email) {
		boolean st = false;

		try {
			connectDB();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM "
					+ table + " WHERE email=?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getInt("admin") == 1) {

					logger.info(email + " is an admin account");
					st = true;
				} else {
					logger.info(email + " is not an admin account");
					st = false;
				}
			} else {

				logger.error("Record for " + email + " not found");
				st = false;
			}

		} catch (Exception e) {
			logger.error("Error getting user " + email + ": " + e.toString());
			e.printStackTrace();
		}

		return st;
	}
	public static int getGID(String email){
		int st = 0;

			try {
				connectDB();

				PreparedStatement ps = con.prepareStatement("SELECT * FROM "
						+ table + " WHERE email=?");
				ps.setString(1, email);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					st = rs.getInt("gID");
				} else {
					logger.error("Record for " + email + " not found");	
					st = 0;
				}

			} catch (Exception e) {
				logger.error("Error getting user " + email + ": " + e.toString());
				e.printStackTrace();
			}

			return st;
		
	}
	public static boolean checkUser(String email, String password) {
		boolean st = false;
		if ((email.equals("") || email == null)
				|| (password.equals("") || password == null)) {
			return false;
		} else {
			try {
				connectDB();

				PreparedStatement ps = con.prepareStatement("SELECT * FROM "
						+ table + " WHERE email=? and password=?");
				ps.setString(1, email);
				String passEnc = encrypt(password);
				ps.setString(2, (passEnc));
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					logger.info("Record for " + email + " found");
					st = true;
				} else {

					logger.error("Record for " + email + " not found");
					st = false;
				}

			} catch (Exception e) {
				logger.error("Error getting user " + email + ": "
						+ e.toString());
				e.printStackTrace();
			}
		}
		return st;
	}
	public static String createJSONArray(String email){
		String arr = new String();
		JSONArray jArray = new JSONArray();
		if(!Validate.isAdmin(email)){
			Employee e = Validate.createEmployee(email);			
		
			JSONObject emp = new JSONObject();
			
			emp.put("fname", e.firstName);
			emp.put("lname", e.lastName);
			emp.put("street", e.street);
			emp.put("city", e.city);
			emp.put("state", e.state);
			emp.put("zip", Integer.toString(e.zipCode));
			emp.put("role", e.employeeRole.toString());
			emp.put("email", email);
			emp.put("admin", "true");
			
			JSONArray mGroup = new JSONArray();
			JSONObject mGroupObj = new JSONObject();
			try{
				connectDB();
				int mID = getID(email);
				PreparedStatement ps = con.prepareStatement("SELECT * FROM userdb.group WHERE mID=?");
				ps.setLong(1, mID);

				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					mGroupObj.put("name", rs.getObject("name"));
					mGroup.add(mGroupObj);	
					mGroupObj = new JSONObject();
				}
				
			}catch(Exception ee){
				logger.error(ee.toString());
			}
			
			JSONObject group = new JSONObject();
			try{
				connectDB();
				int gID = getGID(email);
				PreparedStatement ps = con.prepareStatement("SELECT * FROM userdb.group WHERE id=?");
				ps.setLong(1, gID);

				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					group.put("name", rs.getObject("name"));
										
				}
				
			}catch(Exception ee){
				logger.error(ee.toString());
			}
			
			jArray.add(emp);
			jArray.add(mGroup);
			jArray.add(group);
			
			arr =  jArray.toJSONString();
		}
		return arr;
	}
	
public static String createJSONEmp(String email){
		Employee e = Validate.createEmployee(email);
		String admin = Boolean.toString(Validate.isAdmin(email));
	
		JSONObject emp = new JSONObject();
		
		emp.put("fname", e.firstName);
		emp.put("lname", e.lastName);
		emp.put("street", e.street);
		emp.put("city", e.city);
		emp.put("state", e.state);
		emp.put("zip", Integer.toString(e.zipCode));
		emp.put("role", e.employeeRole.toString());
		emp.put("email", email);
		emp.put("admin", admin);
		
		String json = emp.toJSONString();
		
		return json;
		
		
	}
	public static boolean matchPassword(String pass1, String pass2) {

		if (pass1.equals(pass2)) {
			return true;
		} else {
			return false;

		}
	}
	public static boolean isLoggedIn(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		
		String cookieName = "email";  
		  
		Cookie myCookie = null;  
		if (cookies != null)  
		{  
			for (int i = 0; i < cookies.length; i++)   
			{  
				if (cookies [i].getName().equals (cookieName))  
				{  
					myCookie = cookies[i];  
					break;  
				}  
			}  
		}  
		if(myCookie == null){
			return false;
		}
		else{
			return true;
		}
	}
	public static boolean doesExist(String email) {
		boolean res = false;
		try {
			connectDB();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM "
					+ table + " WHERE email=?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();
			res = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static boolean registerUser(String email, String password,
			String fname, String lname, String street, String city,
			String state, int zip, String role) {
		boolean res = false;
		if (doesExist(email)) {
			return false;
		} else {
			try {
				connectDB();
				PreparedStatement ps = con.prepareStatement("INSERT INTO "
						+ table + "(email,password,fname,lname,street,"
						+ "city,state,zip,role) VALUES(?,?,?,?,?,?,?,?,?)");
				ps.setString(1, email);
				ps.setString(2, encrypt(password));
				ps.setString(3, fname);
				ps.setString(4, lname);
				ps.setString(5, street);
				ps.setString(6, city);
				ps.setString(7, state);
				ps.setLong(8, zip);
				ps.setString(9, role);

				ps.execute();
				res = true;
				// Email.send(email, "fake@email.com", "Registration",
				// "You have successfully registered. Please wait to be activated");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	public static boolean validEmail(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public static String encrypt(String s) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());

			byte byteData[] = md.digest();

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (Exception e) {
			// do stuff with the error
			e.printStackTrace();
			System.out.println("Error encrypting");
		}
		return sb.toString();
	}
	public static String getEmpName(int id){
		String name = new String();
		
		try{
			connectDB();
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM employee WHERE id=?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				name = rs.getString("fname") + " " + rs.getString("lname");
			}else{
				logger.info("No employee found by that id");
			}
		}catch(Exception e){
			logger.error(e.toString());
		}
		return name;
	}
	public static boolean isSet(Object o) {

		boolean s = false;
		try {
			if (o == null || o == "" || o.toString().equals("")) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Employee createEmployee(String email) {
		String fname = new String();
		String lname = new String();
		String street = new String();
		String city = new String();
		String state = new String();
		String role = new String();
		int zip = 0;
		int gID = 0;
		boolean admin = false;
		boolean active = false;

		try {
			connectDB();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM "
					+ table + " WHERE email=?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				fname = rs.getString("fname");
				lname = rs.getString("lname");
				street = rs.getString("street");
				city = rs.getString("city");
				state = rs.getString("state");
				zip = rs.getInt("zip");
				gID = rs.getInt("gID");
				admin = rs.getBoolean("admin");
				active = rs.getBoolean("active");
				role = rs.getString("role");
			} else {
				// Error getting record
				logger.info("No record with email " + email);
			}
			con.close();
		} catch (Exception e) {
			logger.info("Error querying database: " + e.toString());
		}
		if (role.equalsIgnoreCase("Manager")) {
			return new Manager(fname, lname, street, city, state, zip, email);
		} else if (role.equalsIgnoreCase("QA")) {
			return new QA(fname, lname, street, city, state, zip, email);
		} else if (role.equalsIgnoreCase("Developer")) {
			return new Developer(fname, lname, street, city, state, zip, email);
		} else {
			return null;
		}
	}

	public static void sendObject(Object o) {
		URL url;
		HttpURLConnection urlCon;
		ObjectOutputStream out;
		try {
			url = new URL("http://localhost:8080/EmployeeWeb/Console");
			urlCon = (HttpURLConnection) url.openConnection();

			urlCon.setDoOutput(true); // to be able to write.
			urlCon.setDoInput(true); // to be able to read.

			out = new ObjectOutputStream(urlCon.getOutputStream());
			out.writeObject(o);
			out.close();

		} catch (IOException e) {
			logger.info("Error outputting object: " + e.toString());
			e.printStackTrace();
		}

	}

}
