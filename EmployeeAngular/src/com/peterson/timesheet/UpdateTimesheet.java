package com.peterson.timesheet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class UpdateTimesheet
 */
@WebServlet("/UpdateTimesheet")
public class UpdateTimesheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(UpdateTimesheet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTimesheet() {
        super();
        // TODO Auto-generated constructor stub
        BasicConfigurator.configure();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		logger.info("Updating Timesheet....");
		int id = Integer.parseInt(request.getParameter("id"));
		int app = Integer.parseInt(request.getParameter("approved"));
		int submit = Integer.parseInt(request.getParameter("submitted"));
		
		int sun = Integer.parseInt(request.getParameter("sun"));
		int mon = Integer.parseInt(request.getParameter("mon"));
		int tue = Integer.parseInt(request.getParameter("tue"));
		int wed = Integer.parseInt(request.getParameter("wed"));
		int thu = Integer.parseInt(request.getParameter("thu"));
		int fri = Integer.parseInt(request.getParameter("fri"));
		int sat = Integer.parseInt(request.getParameter("sat"));
		
		int sun_ot = Integer.parseInt(request.getParameter("sun_ot"));
		int mon_ot = Integer.parseInt(request.getParameter("mon_ot"));
		int tue_ot = Integer.parseInt(request.getParameter("tue_ot"));
		int wed_ot = Integer.parseInt(request.getParameter("wed_ot"));
		int thu_ot = Integer.parseInt(request.getParameter("thu_ot"));
		int fri_ot = Integer.parseInt(request.getParameter("fri_ot"));
		int sat_ot = Integer.parseInt(request.getParameter("sat_ot"));
		
		String pdf = request.getParameter("pdf");
		
		if(Timesheet.updateTimesheet(id, app, pdf, sun, mon, tue, wed, thu, fri, sat, sun_ot,
				mon_ot, tue_ot, wed_ot, thu_ot, fri_ot, sat_ot, submit)){
			logger.info("Updated timesheet successfully");
		}else{
			logger.error("Failed to update timesheet");
		}
		
		
	}

}
