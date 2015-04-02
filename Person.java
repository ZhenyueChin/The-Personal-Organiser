package comp2100.ass1;

import java.io.Serializable;

public class Person implements Serializable{
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String date;
	private String month;
	private String birthYear;
	private String email;
	private String address;
	private String image;
	
	public Person() {
		
	}
	
	public Person(String firstName, String lastName, String phoneNumber, String date, String month, String birthday, String email, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.date = date;
		this.month = month;
		this.birthYear = birthday;
		this.email = email;
		this.address = address;
	}
	
	public Person(String firstName, String lastName, String phoneNumber, String date, String month, String birthday, String email, String address, String image) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.date = date;
		this.month = month;
		this.birthYear = birthday;
		this.email = email;
		this.address = address;
		this.image = image;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getNumber() {
		return this.phoneNumber;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getMonth() {
		return this.month;
	}
	
	public String getYear() {
		return this.birthYear;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getFullName() {
		return this.firstName + " " + lastName;
	}
	
	public String getImage() {
		return this.image;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName + " " + phoneNumber + " " + date + " " + month + " " + birthYear + " " + email + " " + address + " " + image;
	}
	
	public String getNames() {
		if (firstName.equals("#") && lastName.equals("#")) {
			return "Unknown";
		}
		
		else if (firstName.equals("#") && !lastName.equals("#")) {
			return lastName;
		}
		
		else if (!firstName.equals("#") && lastName.equals("#")) {
			return firstName;
		}
		
		else {
			return firstName + " " + lastName;
		}
	}
	
	public String getAsiaNames() {
		if (firstName.equals("#") && lastName.equals("#")) {
			return "Unknown";
		}
		
		else if (firstName.equals("#") && !lastName.equals("#")) {
			return lastName;
		}
		
		else if (!firstName.equals("#") && lastName.equals("#")) {
			return firstName;
		}
		
		else {
			return lastName + " " + firstName;
		}
	}
	
	
}
