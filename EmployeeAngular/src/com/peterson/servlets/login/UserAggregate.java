package com.peterson.servlets.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class UserAggregate
 */
@WebServlet("/UserAggregate")
public class UserAggregate extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(UserAggregate.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAggregate() {
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
		String json = Validate.getEmail();
		logger.info("Sending JSON packet of emails: " + json);
		out.write(json);
	}

}
