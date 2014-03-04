package com.peterson.timesheet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.peterson.servlets.login.Validate;

public class Timesheet {
	protected static String host = "jdbc:mysql://localhost/userdb";
	protected static String user = "edward";
	protected static String pass = "";
	protected static Connection con;

	private static Logger logger = Logger.getLogger(Timesheet.class);

	public static void connectDB() throws ClassNotFoundException, SQLException {
		BasicConfigurator.configure();
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
	public static boolean submitTimesheet(int id){
		boolean b = false;
		try{
			connectDB();
			
			PreparedStatement ps = con.prepareStatement("UPDATE timesheet SET submitted=1 WHERE id=?");
			ps.setInt(1, id);
			ps.execute();
			b = true;
		}catch(Exception e){
			logger.error(e.toString());
		}
		return b;
	}
	public static boolean approveTimesheet(int id){
		boolean b = false;
		try{
			connectDB();
			
			PreparedStatement ps = con.prepareStatement("UPDATE timesheet SET approved=1 WHERE id=?");
			ps.setInt(1, id);
			ps.execute();
			b = true;
		}catch(Exception e){
			logger.error(e.toString());
		}
		return b;
	}
	public static boolean uploadPDF(int id, String localPath) {
		boolean b = false;
		try {
			connectDB();
			logger.info("Updating database record: "+ localPath + " " + id);
			PreparedStatement ps = con
					.prepareStatement("UPDATE userdb.timesheet SET pdf=? WHERE id=?");
			ps.setString(1, localPath);
			ps.setInt(2, id);
			ps.execute();

			b = true;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;
	}
	
	public static boolean updateTimesheet(int tID, int app, String pdf,
			int sun, int mon, int tue, int wed, int thu, int fri, int sat,
			int sun_ot, int mon_ot, int tue_ot, int wed_ot, int thu_ot, int fri_ot, int sat_ot,
			int submit) {
		boolean b = false;
		try {
			connectDB();

			PreparedStatement ps = con
					.prepareStatement("UPDATE timesheet SET approved=?,"
							+ "pdf=?, sun=?, mon=?, tue=?, wed=?, thu=?, fri=?, sat=?,"
							+ "sun_ot=?, mon_ot=?, tue_ot=?, wed_ot=?, thu_ot=?, fri_ot=?,"
							+ "sat_ot=?, submitted=? WHERE id=?");

			ps.setInt(1, app);
			ps.setString(2, pdf);
			ps.setInt(3, sun);
			ps.setInt(4, mon);
			ps.setInt(5, tue);
			ps.setInt(6, wed);
			ps.setInt(7, thu);
			ps.setInt(8, fri);
			ps.setInt(9, sat);
			
			ps.setInt(10, sun_ot);
			ps.setInt(11, mon_ot);
			ps.setInt(12, tue_ot);
			ps.setInt(13, wed_ot);
			ps.setInt(14, thu_ot);
			ps.setInt(15, fri_ot);
			ps.setInt(16, sat_ot);
			
			ps.setInt(17, submit);
			ps.setInt(18, tID);

			logger.info("Updating timesheet");
			ps.execute();
			b = true;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;

	}

	public static String getTimesheet(int emp_id) {
		JSONObject ts = new JSONObject();
		JSONArray complete = new JSONArray();
		try {
			connectDB();
			// Do Timesheets Exist?
			PreparedStatement ps3 = con
					.prepareStatement("SELECT * FROM timesheet WHERE emp_id=?");
			ps3.setInt(1, emp_id);
			ResultSet rs3 = ps3.executeQuery();

			if (rs3.next()) {
				// If they exist is there a record for the current week?

				ps3 = con
						.prepareStatement("SELECT * FROM timesheet WHERE yearweek=YEARWEEK(CURDATE())");
				rs3 = ps3.executeQuery();
				boolean isCurWeek = rs3.next();

				if (isCurWeek) {
					// If there is:
					PreparedStatement ps = con
							.prepareStatement("SELECT * FROM timesheet WHERE emp_id=? ORDER BY id");
					ps.setInt(1, emp_id);
					logger.info("Pulling timesheet data");
					ResultSet rs = ps.executeQuery();
					logger.info("Timesheet found");
					while (rs.next()) {
						ts.put("id", rs.getInt("id"));
						ts.put("approved", rs.getString("approved"));
						ts.put("submitted", rs.getInt("submitted"));
						ts.put("pdf", rs.getString("pdf"));
						ts.put("sun", rs.getInt("sun"));
						ts.put("mon", rs.getInt("tue"));
						ts.put("tue", rs.getInt("wed"));
						ts.put("wed", rs.getInt("wed"));
						ts.put("thu", rs.getInt("thu"));
						ts.put("fri", rs.getInt("fri"));
						ts.put("sat", rs.getInt("sat"));
						ts.put("week", convertWeek(rs.getInt("yearweek")));
						
						ts.put("sun_ot", rs.getInt("sun_ot"));
						ts.put("sat_ot", rs.getInt("sat_ot"));
						ts.put("mon_ot", rs.getInt("mon_ot"));
						ts.put("tue_ot", rs.getInt("tue_ot"));
						ts.put("wed_ot", rs.getInt("wed_ot"));
						ts.put("thu_ot", rs.getInt("thu_ot"));
						ts.put("fri_ot", rs.getInt("fri_ot"));
						complete.add(ts);
						ts = new JSONObject();

					}
					
					return complete.toJSONString();
				} else {
					// Create one for this week
					ps3 = con
							.prepareStatement("INSERT INTO timesheet(emp_id,yearweek) VALUES(?,YEARWEEK(CURDATE()))");
					ps3.setInt(1, emp_id);
					ps3.execute();
					logger.info("Created new timesheet");
					getTimesheet(emp_id);
				}
			} else {
				// No timesheets. Create some!
				Calendar cal = Calendar.getInstance();
				int curWeek = cal.get(Calendar.WEEK_OF_YEAR);
				int curYear = cal.get(Calendar.YEAR);

				for (int i = 1; i <= curWeek; i++) {

					PreparedStatement ps2 = con
							.prepareStatement("INSERT INTO timesheet(emp_id,yearweek) VALUES(?,YEARWEEK(DATE_ADD(MAKEDATE(?, 1), INTERVAL ? WEEK)))");
					ps2.setInt(1, emp_id);
					ps2.setInt(2, curYear);
					ps2.setInt(3, i);
					ps2.execute();
					logger.info("Created new timesheet");
					
				}
				getTimesheet(emp_id);

			}
			con.close();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		return complete.toJSONString();
	}
	public static String getSubmittedTimesheet(){
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		
		try{
			connectDB();
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM timesheet WHERE submitted=1");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				obj.put("id", rs.getInt("id"));
				obj.put("emp_id", rs.getInt("emp_id"));
				obj.put("week", convertWeek(rs.getInt("yearweek")));
				obj.put("emp", Validate.getEmpName(rs.getInt("emp_id")));
				obj.put("pdf", rs.getString("pdf"));
				obj.put("submitted", rs.getInt("submitted"));
				obj.put("approved", rs.getInt("approved"));
				arr.add(obj);
				obj = new JSONObject();
			}
			
			
		}catch (Exception e){
			logger.error(e.toString());
		}
		
		return arr.toJSONString();
	}
	public static String convertWeek(int yearWeek) {
		String weekDate = new String();
		int year = yearWeek / 100;

		String tmpWeek = Integer.toString(yearWeek);
		tmpWeek = tmpWeek.substring(4);
		int week = Integer.parseInt(tmpWeek);
		logger.info("Getting date of week " + week + " of year " + year);

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		weekDate = sdf.format(cal.getTime()).toString();
		logger.info("Week of: " + weekDate);

		return weekDate;
	}
}
