package com.peterson.timesheet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.peterson.servlets.login.Validate;

/**
 * Servlet implementation class FetchTimesheet
 */
@WebServlet("/FetchSubmitTimesheet")
public class FetchSubmitTimesheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(FetchTimesheet.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchSubmitTimesheet() {
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
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();	
		
		String email = null;
		
		for(Cookie cookie : cookies){
		    if("email".equals(cookie.getName())){
		        email = cookie.getValue();
		    }
		}
		logger.info("Requested cookie for: " + email);
		
		if(email != null){
			String ts = Timesheet.getSubmittedTimesheet();
			logger.info("Dispatching: " + ts);
			out.write(ts);
		}else{
			logger.info("Unable to retrieve timesheet for unregistered email");
			
		}
	}

}
