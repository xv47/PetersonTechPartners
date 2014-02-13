package com.peterson.servlets.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.peterson.employee.Employee;
import com.peterson.employee.Employee.Role;
import com.peterson.group.Group;

/**
 * Servlet implementation class Console
 */
@WebServlet(name="Console", urlPatterns={"/Console"})
public class Console extends HttpServlet {
	private static final long serialVersionUID = 1L;
     Logger logger = Logger.getLogger(Console.class);  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Console() {
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
		boolean logged = Validate.isLoggedIn(request);
		if(logged){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			Cookie[] cookies = request.getCookies();							
			
			String email = null;
			
			for(Cookie cookie : cookies){
			    if("email".equals(cookie.getName())){
			        email = cookie.getValue();
			    }
			}
			logger.info("Requested cookie for: " + email);
			//email = request.getAttribute("email").toString();				
			if(email != null){		
				String json = Validate.createJSONArray(email);
				logger.info("Sending JSON POST packet: " + json);
				out.write(json);
			}else{
				RequestDispatcher rs = request.getRequestDispatcher("login.html");
				rs.include(request, response);
			}
			
		}else{
			RequestDispatcher rs = request.getRequestDispatcher("login.html");
			rs.include(request, response);
		}
		
	}

}
