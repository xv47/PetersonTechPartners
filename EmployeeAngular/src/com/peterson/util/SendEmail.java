package com.peterson.util;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
* Simple demonstration of using the javax.mail API.
*
* Run from the command line. Please edit the implementation
* to use correct email addresses and host name.
*/
public class SendEmail
{
	private String to = "";
	private String from = "";
	private String subject = "";
	private String msg = "";
	private String host = "localhost";
	
	public void setFrom(String from){
		this.from = from;
	}
	public void setHeaders(String to, String subject, String msg){
		this.to = to;
		this.subject = subject;
		this.msg = msg;
	}
	public void sendEmail(){
		// Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);
	      session.setDebug(true);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Now set the actual message
	         message.setText(msg);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	    	  System.out.println(mex.toString());
	         
	      }
	}
	public static void main(String[] args){
		String to = new String();
		String from = new String();
		String subj = new String();
		String msg = new String();
		
		Scanner scan = new Scanner(System.in);
		SendEmail email = new SendEmail();
		
		System.out.println("To:");
		to = scan.nextLine();
		
		System.out.println("From:");
		from = scan.nextLine();
		email.setFrom(from);
		
		System.out.println("Subject:");
		subj = scan.nextLine();
		
		System.out.println("Message:");
		msg = scan.nextLine();
		
		email.setHeaders(to, subj, msg);
		email.sendEmail();
	}
}