package com.epam.gym.atlass_gym.dao;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.service.PasswordGenerator;

public class TraineeDAO {

	private Map<Long, Trainee> trainees = new HashMap<Long, Trainee>();
	
	public TraineeDAO() {}
	
	//create trainee
	public void createTrainee(String firstName, String lastName, LocalDateTime dateOfBirth, String address) {
		Long newId = 1L;
		if(trainees.size()>0) {
			newId = trainees.get(trainees.keySet().stream().max(Comparator.naturalOrder()).get()-1).getUserId()+1;
		}
		
		try {
			selectTrainee(firstName+"."+lastName);
			trainees.put(newId,new Trainee(firstName,lastName, firstName+"."+lastName+"1", PasswordGenerator.generatePassword(), dateOfBirth, address, newId));

		}catch(NoSuchElementException e) {
			trainees.put(newId,new Trainee(firstName,lastName, firstName+"."+lastName, PasswordGenerator.generatePassword(), dateOfBirth, address, newId));

		}
			
	}
	
	public void createTrainee(Trainee trainee) {
		trainees.put(trainee.getUserId(),trainee);
		
	}
		
	//update trainee
	public void updateTrainee(String username, String newFirstName, String newLastName, LocalDateTime newDateOfBirth, String newAddress) {
		Trainee t = selectTrainee(username);
		t.setFirstName(newFirstName);
		t.setLastName(newLastName);
		t.setDateOfBirth(newDateOfBirth);
		t.setAddress(newAddress);
			
	}
		
	//remove trainee
	public void deleteTrainee(String username) {
		trainees.remove(selectTrainee(username).getUserId());
			
	}
		
	
	//get trainee
	public Trainee selectTrainee(String username) {
		return trainees.keySet().stream()
		  .filter(k -> trainees.get(k).getUsername().equals(username))
		  .findFirst().map(k -> trainees.get(k))
		  .get();
	}
	
	//get trainee map
	public Map<Long, Trainee> selectTrainees() {
		return trainees;
	}

	
}
