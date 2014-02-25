package com.peterson.employee;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.peterson.servlets.login.Validate;

/**
 * Servlet implementation class AdminUserReject
 */
@WebServlet(name = "AdminUserReject", urlPatterns = { "/AdminUserReject" })
public class AdminUserReject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AdminUserReject.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUserReject() {
		super();
		// TODO Auto-generated constructor stub
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
		BasicConfigurator.configure();

		int userToApprove = Integer.parseInt(request.getParameter("userID"));

		logger.info("Attempting to reject record for employee id="
				+ userToApprove);
		if (Validate.rejectUser(userToApprove)) {
			logger.info("Record deleted");
			RequestDispatcher rs = request.getRequestDispatcher("console.html");
			rs.include(request, response);
		} else {
			logger.info("Unable to delete record");
			RequestDispatcher rs = request.getRequestDispatcher("console.html");
			rs.include(request, response);
		}
	}

}
