package com.epam.gym.atlass_gym.model;

import com.epam.gym.atlass_gym.service.PasswordGenerator;

public class Trainer extends User{
	
	
	private Training_type specialisation;
	
	private Long userId;
	
	public Trainer() {
		super();
	}
	public Trainer(String firstName, String lastName, String username, Training_type specialisation, Long userId) {
		super(firstName, lastName, username, PasswordGenerator.generatePassword());
		this.specialisation = specialisation;
		this.userId = userId;
		
	}
	
	public Long getUserId() {return userId;}
	public void setUserId(Long userId) {this.userId = userId;}
	public Training_type getSpecialisation() {return specialisation;}
	public void setSpecialisation(Training_type specialisation) {this.specialisation = specialisation;}
	
	@Override 
	public String toString() {
		return getFirstName()+" "+getLastName()+" "+getUsername()+" "+getPassword()+" "+specialisation.getTraining_type()+" "+userId; 
		
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
		result = prime * result + ((specialisation == null) ? 0 : specialisation.hashCode());
		result = prime * result + userId.intValue();
		return result;
    }
}
