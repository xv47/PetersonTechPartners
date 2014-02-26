package com.peterson.employee;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet(name = "UploadFile", urlPatterns = { "/UploadFile" })
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(UploadFile.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadFile() {
		super();
		BasicConfigurator.configure();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		logger.info("(GET) Email set as: " + request.getParameter("email"));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Get the email id:
		String email = new String();
		String emailHash = new String();
		String localPath = new String();
		

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				// Parse the request
				List /* FileItem */items = upload.parseRequest(request);
				logger.info(items.toString());
				Iterator iterator = items.iterator();
				while (iterator.hasNext()) {
					FileItem item = (FileItem) iterator.next();
					if(item.isFormField()){
						if(item.getFieldName().equals("email")){
							email = item.getString();
							logger.info("Email set as: " + email);
						}
					}
					if (!item.isFormField()) {
						//String fileName = item.getName();
						// String root = getServletContext().getRealPath("/");
						// File path = new File(root + "/uploads");
						
						emailHash = UploadFileControl.nameHash(email);
						String fileName = UploadFileControl.imgRename(emailHash, item.getName());
						//Save img name as cookie:
						Cookie cookie = new Cookie("imgName", fileName);
						response.addCookie(cookie);
						
						logger.info("Uploaded file being saved as: " + fileName);
						File path = new File(
								"C:/Users/Osiris/Pictures/EmployeePics/" + emailHash +"/");
						if (!path.exists()) {
							boolean status = path.mkdirs();
						}

						File uploadedFile = new File(path + "/" + fileName);
						logger.info("Uploading to: " + uploadedFile.toString());
						logger.info(uploadedFile.getAbsolutePath());
						item.write(uploadedFile);
					}
				}
			} catch (FileUploadException e) {
				logger.error(e.toString());
				e.printStackTrace();
			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}
}
