package com.epam.gym.atlass_gym.model;


public class User {

	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean isActive; 	
	
	public User() {
		isActive = true;
	}
	public User(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		isActive = true;
	}
	
	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getPassword() {return password;}
	public boolean isActive() {return isActive;}
	public void setActive(boolean isActive) {this.isActive = isActive;}
	public void toggleActive() {this.isActive = !isActive;}
}
