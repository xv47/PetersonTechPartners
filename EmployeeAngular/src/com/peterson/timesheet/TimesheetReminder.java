package com.peterson.timesheet;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.peterson.util.SendEmail;

public class TimesheetReminder implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		SendEmail email = new SendEmail();
		email.setFrom("root@localhost");
		email.setHeaders("esgolba@gmail.com", "Test", "This is a test");
		email.sendEmail();
	}

}
