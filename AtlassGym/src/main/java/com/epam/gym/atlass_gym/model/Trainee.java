package com.epam.gym.atlass_gym.model;

import java.time.LocalDateTime;

import com.epam.gym.atlass_gym.service.PasswordGenerator;


public class Trainee extends User{


	private LocalDateTime dateOfBirth;
	private String address;
	private Long userId;
	
	public Trainee() {
		super();
	}
	public Trainee(String firstName, String lastName, String username, LocalDateTime dateOfBirth, String address, Long userId) {
		super(firstName, lastName, username, PasswordGenerator.generatePassword());
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
	
	@Override
    public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((super.getFirstName() == null) ? 0 : super.getFirstName().hashCode());
		result = prime * result + ((super.getLastName() == null) ? 0 : super.getLastName().hashCode());
		result = prime * result + ((super.getUsername() == null) ? 0 : super.getUsername().hashCode());
		result = prime * result + ((super.getPassword() == null) ? 0 : super.getPassword().hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + userId.intValue();
		return result;
    }
	
}
