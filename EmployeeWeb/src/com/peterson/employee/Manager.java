package com.peterson.employee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;


public class Manager extends Employee {	
	private int gID = 0;
	
	public Manager(String fName, String lName, String street, String city, String state, int zip, String email) {
		super(fName, lName,Role.MANAGER, street, city,state, zip, email);
		super.employeeRole = Role.MANAGER;
		Employee.employeeList.add(this);
		
	}
	public void addProjects(int project){
		
	}

	public void addSubordinate(Employee e){
		
		
	}


}
