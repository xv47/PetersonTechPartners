package com.peterson.group;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.peterson.employee.Employee;
import com.peterson.servlets.login.Validate;

/**
 * Servlet implementation class AddGroupMember
 */
@WebServlet("/AddGroupMember")
public class AddGroupMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AddGroupMember.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddGroupMember() {
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
		PrintWriter out= response.getWriter();
		String email = request.getParameter("email");
		//String group = request.getParameter("group");
		Cookie[] cookies = request.getCookies();

		String groupID = null;

		for (Cookie cookie : cookies) {
			if ("groupID".equals(cookie.getName())) {
				groupID = cookie.getValue();
			}
		}
		logger.info("Group ID: " + groupID);
		logger.info("Email: " + email);
		if (email.length() > 0) {
			if (Validate.validEmail(email)) {
				if (Group.joinGroup(Integer.parseInt(groupID), email)) {
					logger.info("Added " + email + " to group " );
				} else {
					logger.error("Unable to add member");
				}
			} else {
				logger.error("Invalid email type");
			}
		}
		Employee e = Validate.createEmployee(email);
		JSONObject jObj = new JSONObject();
		jObj.put("fname", e.firstName);
		jObj.put("lname", e.lastName);
		jObj.put("email", email);
		jObj.put("id", Validate.getID(email));
		
		String output = jObj.toJSONString();
		out.write(output);
		
	}

}
