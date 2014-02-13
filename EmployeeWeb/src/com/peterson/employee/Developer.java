package com.peterson.employee;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class Developer extends Employee {	
	
	public Developer(String fName, String lName, String street, String city, String state, int zip, String email) {
		super(fName, lName, Role.DEVELOPER,street, city, state, zip, email);
		super.employeeRole=Role.DEVELOPER;
		Employee.employeeList.add(this);
	}
	
	
}
