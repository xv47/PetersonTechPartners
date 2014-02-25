package com.peterson.servlets.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(Logout.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        BasicConfigurator.configure();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		Cookie[] cookies = request.getCookies();							
		
		String cookieName = "email";  
		
		Cookie myCookie = null;  
		if (cookies != null)  
		{  
			for (int i = 0; i < cookies.length; i++)   
			{  
				if (cookies [i].getName().equals (cookieName))  
				{  
					myCookie = cookies[i];  
					break;  
				}  
			}  
		}  

		myCookie.setMaxAge(0); 

		logger.info("Getting cookie for logout: ");
		
       
        logger.info("User logged out");
        response.sendRedirect("login.html");
        
	
	}

}
