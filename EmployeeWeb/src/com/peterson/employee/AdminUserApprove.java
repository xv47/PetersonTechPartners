package com.peterson.employee;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class AdminUserApprove
 */
@WebServlet(name="AdminUserApprove", urlPatterns={"/AdminUserApprove"})
public class AdminUserApprove extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(AdminUserApprove.class);
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserApprove() {
        super();
        // TODO Auto-generated constructor stub
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
		BasicConfigurator.configure();
				
		
		int userToApprove = Integer.parseInt(request.getParameter("userID"));
		logger.info("Attempting to approve record for employee id=" + userToApprove);
		if(Validate.approveUser(userToApprove)){
			logger.info("Record updated to approved status");
			RequestDispatcher rs = request.getRequestDispatcher("console.html");
			rs.include(request, response);
		}else{
			logger.info("Unable to update record as approved");
			RequestDispatcher rs = request.getRequestDispatcher("console.html");
			rs.include(request, response);
		}
	}

}
