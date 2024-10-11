package com.epam.gym.atlass_gym.model;



public class Trainer extends User{
	
	
	private Training_type specialisation;
	
	private Long userId;
	
	public Trainer() {
		super();
	}
	public Trainer(String firstName, String lastName, String username, String password, Training_type specialisation, Long userId) {
		super(firstName, lastName, username, password);
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
}
