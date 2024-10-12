package com.epam.gym.atlass_gym.model;

import java.time.LocalDateTime;


public class Trainee extends User{


	private LocalDateTime dateOfBirth;
	private String address;
	private Long userId;
	
	public Trainee() {
		super();
	}
	public Trainee(String firstName, String lastName, String username, String password, LocalDateTime dateOfBirth, String address, Long userId) {
		super(firstName, lastName, username, password);
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.userId = userId;
	}
	
	public LocalDateTime getDateOfBirth() {return dateOfBirth;}
	public void setDateOfBirth(LocalDateTime dateOfBirth) {this.dateOfBirth = dateOfBirth;}
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}
	public Long getUserId() {return userId;}
	public void setUserId(Long userId) {this.userId = userId;}
	
	@Override 
	public String toString() {
		return getFirstName()+" "+getLastName()+" "+getUsername()+" "+getPassword()+" "+dateOfBirth.toString()+" "+address+" "+userId; 
		
	}
	
	@Override 
	public boolean equals(Object obj) {
		
		return obj.toString().equals(toString());
	}
	
}
