package com.peterson.group;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.peterson.servlets.login.Validate;

/**
 * Servlet implementation class JoinGroupServlet
 */
@WebServlet("/JoinGroupServlet")
public class JoinGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(JoinGroupServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JoinGroupServlet() {
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
		String groupName = request.getParameter("groupCname");
		Group.init();
		Cookie[] cookies = request.getCookies();

		logger.info("Adding to group: " + groupName);
		String email = null;

		for (Cookie cookie : cookies) {
			if ("email".equals(cookie.getName())) {
				email = cookie.getValue();
			}
		}
		logger.info("Cookie retrieved for: " + email);

		
		// Get the printwriter object from response to write the required json object to the output stream      

		if (groupName.length() > 0) {
			if (Group.doesGroupExist(groupName)) {
				if (Group.joinGroup(groupName, email)) {
					logger.info("Group " + groupName + " joined");
					
				} else {
					logger.error("Unable to add group " + groupName);
					
				}
			} 
		} 				
		response.sendRedirect("console.html");
	}

}
