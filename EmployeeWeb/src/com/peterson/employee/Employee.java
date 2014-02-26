package com.peterson.employee;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Employee {
	public enum Role{MANAGER, DEVELOPER, QA}
	
	protected static ArrayList<Employee> employeeList = new ArrayList<Employee>();

	public String firstName = new String();
	
	public String lastName = new String();
	
	public String street = new String();
	public String city = new String();
	public String state = new String();
	public int zipCode = 0;
	public String email = new String();
	public String img = new String();
	
	public Role employeeRole;
	
	public Employee(String fName, String lName, Role role, String street, String city, String state, int zip, String email, String img){		
		firstName = fName;
		lastName = lName;
		employeeRole = role;
		
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zip;
		this.email = email;	
		this.img = img;

	}

	public void updateRecord(Employee e,int id){
		
		
	}
	public void saveRecord(Employee e){
		
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(Role employeeRole) {
		this.employeeRole = employeeRole;
	}
	
		
}
