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
 * Servlet implementation class CreateGroupServlet
 */
@WebServlet("/CreateGroupServlet")
public class CreateGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      Logger logger = Logger.getLogger(CreateGroupServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateGroupServlet() {
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
		if(Group.addGroup(groupName, Validate.getID(email))){
			logger.info("Group " + groupName + " added");
		}else{
			logger.error("Unable to add group " + groupName);
		}
		RequestDispatcher rs = request.getRequestDispatcher("console.html");
		rs.include(request, response);
		
	}

}
