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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.peterson.employee.Employee;
import com.peterson.employee.UploadFileControl;

/**
 * Servlet implementation class Register
 */
@WebServlet(name="Register", urlPatterns={"/Register"})
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(Register.class);   
   

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BasicConfigurator.configure();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();	
		
		RequestDispatcher rs;
		
		String email=new String(), pass1 = new String(), pass2 = new String(), fname=new String(),lname=new String(),
				street=new String(), city=new String(), state=new String(),role=new String(),imgPath = new String();
		
		
		email = request.getParameter("email");
		pass1 = request.getParameter("pass1");
		pass2 = request.getParameter("pass2");
		
		fname = request.getParameter("fname");
		lname = request.getParameter("lname");
		street = request.getParameter("street");
		city = request.getParameter("city");
		state = request.getParameter("state");
		Integer zip = Integer.parseInt(request.getParameter("zip"));
		role = request.getParameter("role");
		
		String emailHash = UploadFileControl.nameHash(email);
		
		Cookie[] cookies = request.getCookies();							
		
		String imgName = null;
		
		for(Cookie cookie : cookies){
		    if("imgName".equals(cookie.getName())){
		        imgName = cookie.getValue();
		    }
		}
		
		logger.info("Profile image name: " + imgName);
		imgPath = "/EmpPics/" + emailHash + "/" + imgName ;	
		
		
		if(!pass1.equals(pass2)){
			out.println("<span class='label label-warning'>Passwords do not match</span>");
			rs = request.getRequestDispatcher("login.html");
			rs.include(request, response);
		}else{
			////////////////////////////////////////////////////////////////////////////////////////
			//
			// 02/15/2014
			//
			// Uncomment the following block to allow records to be added to the database
			//
			////////////////////////////////////////////////////////////////////////////////////////
			
			if(Validate.registerUser(email, pass1, fname, lname, street, city, state, zip, role,imgPath)){
				Employee emp = Validate.createEmployee(email);
				
				Validate.sendObject(emp);
				rs = request.getRequestDispatcher("console.html");
				rs.include(request, response);
			}
			else{
				out.println("<div class='row'><div class='col-md-4 col-md-offset-4'>"
						+ "<div class='alert alert-danger text-center'>Email already registered</div></div></div>");
				rs = request.getRequestDispatcher("login.html");
				rs.include(request, response);
			}
			
			
		}
	}

}
