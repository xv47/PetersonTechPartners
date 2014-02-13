package com.peterson.servlets.login;

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

/**
 * Servlet implementation class Login
 */
@WebServlet(name="Login", urlPatterns={"/Login"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(Login.class);
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				Validate.init();				
				
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				
				if(Validate.checkUser(email,password)){
					Cookie cookie = new Cookie("email", email);
					response.addCookie(cookie);					
					
					logger.info("User: " + email + " logged in successfully.");
					RequestDispatcher rs = request.getRequestDispatcher("console.html");
					rs.include(request, response);
					//response.sendRedirect("console.html");
				} else {
					
					out.println("<div class='row'><div class='col-md-4 col-md-offset-4'>"
							+ "<div class='alert alert-danger text-center'>Username or Password incorrect</div></div></div>");
					RequestDispatcher rs = request.getRequestDispatcher("login.html");
					rs.include(request, response);
					
				}
	}

}
