package com.peterson.group;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class GetGroupMembers
 */
@WebServlet("/GetGroupMembers")
public class GetGroupMembers extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(GetGroupMembers.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGroupMembers() {
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
		Cookie[] cookies = request.getCookies();							
		
		String groupID = null;
		
		for(Cookie cookie : cookies){
		    if("groupID".equals(cookie.getName())){
		        groupID = cookie.getValue();
		    }
		}
		logger.info("Getting members for groupID: " + groupID);
		if(groupID != null){
			String array = Group.getGroupMembers(Integer.parseInt(groupID)).toJSONString();
			logger.info("Sending JSON Group member packet: " + array);
			out.write(array);
		}
	}

}
