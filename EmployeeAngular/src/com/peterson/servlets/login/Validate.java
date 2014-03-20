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
import com.peterson.employee.Employee.Role;
import com.peterson.employee.Manager;
import com.peterson.employee.QA;
import com.peterson.util.MySQL;

public class Validate {
	protected static String host = "jdbc:mysql://localhost/userdb";
	protected static String user = "edward";
	protected static String pass = "";
	protected static String table = "employee";
	protected static Connection con;

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

	public static String emailCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		String email = null;

		for (Cookie cookie : cookies) {
			if ("email".equals(cookie.getName())) {
				email = cookie.getValue();
			}
		}
		return email;
	}

	public static String getEmail() {
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();

		try {
			connectDB();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM "
					+ table);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				obj.put("email", rs.getString("email"));
				arr.add(obj);
				obj = new JSONObject();
			}
			con.close();
		} catch (Exception e) {
			logger.error(e);
		}

		return arr.toJSONString();

	}

	public static int getID(String email) {
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
			con.close();
		} catch (Exception e) {
			logger.error("Error getting user " + email + ": " + e.toString());
			e.printStackTrace();
		}

		return st;
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
			con.close();
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
			con.close();
		} catch (Exception e) {
			logger.error("Error getting user " + email + ": " + e.toString());
			e.printStackTrace();
		}

		return st;
	}

	public static int getGID(String email) {
		int st = 0;

		try {
			connectDB();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM "
					+ table + " WHERE email=?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				st = (int) rs.getLong("gID");
				logger.info("Employee group id is: " + st);
			} else {
				logger.error("Record for " + email + " not found");
				st = 0;
			}
			con.close();
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
				con.close();

			} catch (Exception e) {
				logger.error("Error getting user " + email + ": "
						+ e.toString());
				e.printStackTrace();
			}
		}
		return st;
	}

	public static String createJSONArray(String email) {
		String arr = new String();
		Employee e = Validate.createEmployee(email);
		JSONArray jArray = new JSONArray();

		JSONObject emp = new JSONObject();

		emp.put("fname", e.firstName);
		emp.put("lname", e.lastName);
		emp.put("street", e.street);
		emp.put("city", e.city);
		emp.put("state", e.state);
		emp.put("zip", Integer.toString(e.zipCode));
		emp.put("role", e.employeeRole.toString());
		emp.put("email", email);
		emp.put("admin", String.valueOf(isAdmin(email)));
		emp.put("img", e.img);

		JSONArray mGroup = new JSONArray();
		JSONObject mGroupObj = new JSONObject();

		JSONArray mGroupEmployee = new JSONArray();
		if (e.employeeRole == Role.MANAGER) {
			try {
				connectDB();
				int mID = getID(email);
				int groupID = 0;
				PreparedStatement ps = con
						.prepareStatement("SELECT * FROM userdb.group WHERE mID=?");
				ps.setLong(1, mID);

				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					mGroupObj.put("name", rs.getString("name"));
					mGroupObj.put("id", rs.getInt("id"));
					mGroup.add(mGroupObj);
					mGroupObj = new JSONObject();
				}

			} catch (Exception ee) {
				logger.error(ee.toString());
			}
		}
		// Get groups for Dev and QA
		JSONObject group = new JSONObject();
		try {
			connectDB();
			int gID = getGID(email);
			logger.info("Getting group id: " + gID);
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM userdb.group WHERE id=?");
			ps.setLong(1, gID);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				group.put("name", rs.getString("name"));
				group.put("mgr", getEmpName((int) rs.getLong("mID")));
				group.put("completed", rs.getLong("complete"));

			}
			logger.info(group.toJSONString());
		} catch (Exception ee) {
			logger.error(ee.toString());
		}

		// Array Order:
		// 1) Personal info
		// 2) Managers Group settings
		// 3) Dev/QA Group memebership
		// 4) Admin Users to approve
		//
		jArray.add(emp);
		jArray.add(mGroup);
		jArray.add(group);

		if (Validate.isAdmin(email)) {

			JSONArray adminUsers = new JSONArray();
			JSONObject adminUser = new JSONObject();
			// Not looping here?
			try {
				connectDB();
				logger.info("Checking if user is admin for JSON Array");
				if (isAdmin(email)) {
					logger.info("User is admin. Fetching inactive users for population");
					PreparedStatement ps = con
							.prepareStatement("SELECT * FROM userdb.employee WHERE active=?");
					ps.setLong(1, 0);

					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						adminUser.put("id", rs.getLong("id"));
						adminUser.put("fname", rs.getString("fname"));
						adminUser.put("lname", rs.getString("lname"));
						adminUser.put("street", rs.getString("street"));
						adminUser.put("city", rs.getString("city"));
						adminUser.put("state", rs.getString("State"));
						adminUser.put("zip", (int) rs.getLong("zip"));
						adminUser.put("role", rs.getString("role"));
						adminUser.put("img", rs.getString("img"));
						adminUser.put("email", email);
						adminUsers.add(adminUser);
						adminUser = new JSONObject();

					}
					jArray.add(adminUsers);
				}
				con.close();
			} catch (Exception er) {
				logger.error(er.toString());
			}
		}
		arr = jArray.toJSONString();
		return arr;
	}

	public static boolean approveUser(int id) {
		boolean b = false;
		try {
			connectDB();
			logger.info("Querying for Employee to Approve");

			PreparedStatement ps = con
					.prepareStatement("UPDATE userdb.employee SET active=? WHERE id=?");
			ps.setLong(1, 1);
			ps.setLong(2, id);
			// Uncomment to make it do stuff:
			ps.execute();
			b = true;
			con.close();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;
	}

	public static boolean rejectUser(int id) {
		boolean b = false;
		try {
			connectDB();
			logger.info("Querying for Employee to Reject");

			PreparedStatement ps = con
					.prepareStatement("DELETE FROM userdb.employee WHERE id=?");
			ps.setLong(1, id);

			// Uncomment to make it do stuff:
			ps.execute();
			b = true;
			con.close();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;
	}

	public static String createJSONEmp(String email) {
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

	public static boolean isLoggedIn(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		String cookieName = "email";

		Cookie myCookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					myCookie = cookies[i];
					break;
				}
			}
		}
		if (myCookie == null) {
			return false;
		} else {
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
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static boolean registerUser(String email, String password,
			String fname, String lname, String street, String city,
			String state, int zip, String role, String imgPath) {
		boolean res = false;
		if (doesExist(email)) {
			return false;
		} else {
			try {
				connectDB();
				PreparedStatement ps = con
						.prepareStatement("INSERT INTO "
								+ table
								+ "(email,password,fname,lname,street,"
								+ "city,state,zip,role,img) VALUES(?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, email);
				ps.setString(2, encrypt(password));
				ps.setString(3, fname);
				ps.setString(4, lname);
				ps.setString(5, street);
				ps.setString(6, city);
				ps.setString(7, state);
				ps.setLong(8, zip);
				ps.setString(9, role);
				ps.setString(10, imgPath);

				ps.execute();
				res = true;
				// Email.send(email, "fake@email.com", "Registration",
				// "You have successfully registered. Please wait to be activated");
				con.close();
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

	public static String getEmpName(int id) {
		String name = new String();

		try {
			connectDB();

			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM employee WHERE id=?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				name = rs.getString("fname") + " " + rs.getString("lname");
			} else {
				logger.info("No employee found by that id");
			}
			con.close();
		} catch (Exception e) {
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
		String img = new String();
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
				img = rs.getString("img");
			} else {
				// Error getting record
				logger.info("No record with email " + email);
			}
			con.close();
		} catch (Exception e) {
			logger.info("Error querying database: " + e.toString());
		}
		if (role.equalsIgnoreCase("Manager")) {
			return new Manager(fname, lname, street, city, state, zip, email,
					img);
		} else if (role.equalsIgnoreCase("QA")) {
			return new QA(fname, lname, street, city, state, zip, email, img);
		} else if (role.equalsIgnoreCase("Developer")) {
			return new Developer(fname, lname, street, city, state, zip, email,
					img);
		} else {
			return null;
		}
	}
	public static String getAllEmployees(){
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		try{
			connectDB();
			
			PreparedStatement ps= con.prepareStatement("SELECT * FROM userdb.employee");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				obj.put("id", rs.getLong("id"));
				obj.put("fname", rs.getString("fname"));
				obj.put("lname", rs.getString("lname"));
				obj.put("street", rs.getString("street"));
				obj.put("city", rs.getString("city"));
				obj.put("state", rs.getString("State"));
				obj.put("zip", (int) rs.getLong("zip"));
				obj.put("role", rs.getString("role"));
				obj.put("img", rs.getString("img"));
				obj.put("email", rs.getString("email"));
				array.add(obj);
				obj = new JSONObject();
			}
			con.close();
		}catch(Exception e){
			logger.error(e.toString());
		}
		return array.toJSONString();
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
