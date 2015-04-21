package org.gmjm.stereotype.example.componenets;

public class Address {
	String streetAddress;
	String city;
	String state;
	String zip;
	public Address(String streetAddress, String city, String state, String zip) {
		super();
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	
	
	
	
	
}
