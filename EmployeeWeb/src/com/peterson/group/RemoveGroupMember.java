package com.peterson.group;

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
 * Servlet implementation class RemoveGroupMember
 */
@WebServlet("/RemoveGroupMember")
public class RemoveGroupMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(RemoveGroupMember.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveGroupMember() {
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
		int id = Integer.parseInt(request.getParameter("id"));
		int gID = Integer.parseInt(request.getParameter("gID"));
		
		logger.info("Attempting to remove user id: " + id + " from group id: " + gID);
		if(id>0){
			if(Group.removeGroupMember(id)){
				logger.info("Successfully Removed");
			}else{
				logger.error("Unable to remove user");
			}
		}
	}

}
