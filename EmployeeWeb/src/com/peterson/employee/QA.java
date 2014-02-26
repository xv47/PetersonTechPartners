package com.peterson.employee;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class QA extends Employee {
	private int gID;
	
	public QA(String fName, String lName, String street, String city, String state, int zip, String email, String img) {
		super(fName, lName, Role.QA, street, city, state, zip, email, img);
		super.employeeRole = Role.QA;
		Employee.employeeList.add(this);
		
	}
	
}
