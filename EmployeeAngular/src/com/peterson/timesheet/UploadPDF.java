package com.peterson.timesheet;

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

import com.peterson.employee.UploadFileControl;

/**
 * Servlet implementation class UploadPDF
 */
@WebServlet(name="UploadPDF", urlPatterns={"/UploadPDF"})
public class UploadPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(UploadPDF.class);
    private boolean isMultipart;
	   private String filePath;
	   private int maxFileSize = 50 * 1024;
	   private int maxMemSize = 4 * 1024;
	   static String message = null;
	   public String fileName = null;
		static int status = 0;
	   private File file ;
	   public void init( ){
		      // Get the file location where it would be stored.
		      filePath = 
		             getServletContext().getInitParameter("file-upload"); 
		   }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadPDF() {
        super();
        // TODO Auto-generated constructor stub
        BasicConfigurator.configure();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String n = request.getParameter("test");
		logger.info(n);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String email = new String();
		String emailHash = new String();
		String localPath = new String();
		int id = 0;
		
		Cookie[] cookies = request.getCookies();							
		
		for(Cookie cookie : cookies){
		    if("email".equals(cookie.getName())){
		        email = cookie.getValue();
		    }
		}
		logger.info("Cookie retrieved for: " + email);
		logger.info("Uploading PDF");
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
						if(item.getFieldName().equals("id")){
							id = Integer.parseInt(item.getString());
							logger.info("Timesheet id set as: " + id);
						}
					}
					if (item.getFieldName().equals("file")) {
						//String fileName = item.getName();
						// String root = getServletContext().getRealPath("/");
						// File path = new File(root + "/uploads");
						
						emailHash = UploadFileControl.nameHash(email);
						String fileName = item.getName();
						//Save img name as cookie:
						
						logger.info("Uploaded file being saved as: " + fileName);
						File path = new File(
								"C:/Users/Osiris/Documents/EmployeeTimesheets/" + emailHash +"/");
						if (!path.exists()) {
							boolean status = path.mkdirs();
						}

						File uploadedFile = new File(path + "/" + fileName);
						logger.info("Uploading to: " + uploadedFile.toString());
						logger.info(uploadedFile.getAbsolutePath());
						item.write(uploadedFile);
						String localpath = "/ETS/" + UploadFileControl.nameHash(email) + "/" + fileName;
						Timesheet.uploadPDF(id, localpath);
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
