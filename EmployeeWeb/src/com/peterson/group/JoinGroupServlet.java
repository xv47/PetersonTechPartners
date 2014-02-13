package com.peterson.group;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

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
		String groupName = request.getParameter("groupCname");
		Group.init();
		Cookie[] cookies = request.getCookies();							
		
		String email = null;
		
		for(Cookie cookie : cookies){
		    if("email".equals(cookie.getName())){
		        email = cookie.getValue();
		    }
		}
		logger.info("Cookie retrieved for: " + email);
		//
		//
		//
		//TO CHANGE!!!!
		//
		//
		//
		if(Group.addGroup(groupName, Validate.getID(email))){
			logger.info("Group " + groupName + " added");
		}else{
			logger.error("Unable to add group " + groupName);
		}
		RequestDispatcher rs = request.getRequestDispatcher("console.html");
		rs.include(request, response);
	}

}
