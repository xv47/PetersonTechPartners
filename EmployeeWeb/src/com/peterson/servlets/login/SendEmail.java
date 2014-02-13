package com.peterson.servlets.login;

import java.io.IOException;

import javax.mail.Message.RecipientType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;
import org.codemonkey.simplejavamail.TransportStrategy;

/**
 * Servlet implementation class SendEmail
 */
@WebServlet("/SendEmail")
public class SendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendEmail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request,

	HttpServletResponse response)

	throws ServletException, IOException

	{

		final Email email = new Email();

		email.setFromAddress("Sears HRM", "firasiano@gmail.com");

		email.setSubject("hey");

		email.addRecipient("C. Cane", "esgolba@gmail.com", RecipientType.TO);

		// email.addRecipient("C. Bo", "chocobo@candyshop.org",
		// RecipientType.BCC);

		email.setText("This iasasdfasdfjladskfj;ladkfj ");

		// email.setTextHTML("<img src='cid:wink1'><b>We should meet up!</b><img src='cid:wink2'>");

		// embed images and include downloadable attachments

		// email.addEmbeddedImage("wink1", imageByteArray, "image/png");

		// email.addEmbeddedImage("wink2", imageDatesource);

		// email.addAttachment("invitation", pdfByteArray, "application/pdf");

		// email.addAttachment("dresscode", odfDatasource);

		new Mailer("smtp.gmail.com", 465, "sublimeminddesign@gmail.com",
				"R3ykj@v!k", TransportStrategy.SMTP_SSL).sendMail(email);

		response.sendRedirect("http://localhost:8080/company/index.htm");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
