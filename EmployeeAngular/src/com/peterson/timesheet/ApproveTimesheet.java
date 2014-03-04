package com.peterson.timesheet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class ApproveTimesheet
 */
@WebServlet("/ApproveTimesheet")
public class ApproveTimesheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(ApproveTimesheet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApproveTimesheet() {
		super();
		// TODO Auto-generated constructor stub
		BasicConfigurator.configure();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			logger.info("Timesheet id to approve: " + id);
			if (Timesheet.approveTimesheet(id)) {
				logger.info("Successfully approved timesheet");
			} else {
				logger.info("Unable to approve timesheet");
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

}
