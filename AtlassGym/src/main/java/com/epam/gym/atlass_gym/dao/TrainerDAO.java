package com.epam.gym.atlass_gym.dao;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training_type;
import com.epam.gym.atlass_gym.service.PasswordGenerator;

public class TrainerDAO {

	private Map<Long, Trainer> trainers = new HashMap<Long, Trainer>();
	
	public TrainerDAO() {}
	
	//create trainer
	public void createTrainer(String firstName, String lastName, Training_type specialisation) {
		Long newId = 1L;
		if(trainers.size()>0) {
			newId = trainers.get(trainers.keySet().stream().max(Comparator.naturalOrder()).get()-1).getUserId()+1;
		}
		
		try {
			selectTrainer(firstName+"."+lastName);
			trainers.put(newId, new Trainer(firstName,lastName, firstName+"."+lastName+"1", PasswordGenerator.generatePassword(), specialisation, newId));

		}catch(NoSuchElementException e) {
			trainers.put(newId, new Trainer(firstName,lastName, firstName+"."+lastName, PasswordGenerator.generatePassword(), specialisation, newId));

		}
	}
	
	public void createTrainer(Trainer trainer) {
		trainers.put(trainer.getUserId(),trainer);
		
	}
	
	//update trainer
	public void updateTrainer(String username, String newFirstName, String newLastName, Training_type newSpecialisation) {
		Trainer t = selectTrainer(username);
		t.setFirstName(newFirstName);
		t.setLastName(newLastName);
		t.setSpecialisation(newSpecialisation);
		
	}
	
	//get trainer
	public Trainer selectTrainer(String username) {
		return trainers.keySet().stream()
		  .filter(k -> trainers.get(k).getUsername().equals(username))
		  .findFirst().map(k -> trainers.get(k))
		  .get();
	}
	
	//get trainer map
	public Map<Long, Trainer> selectTrainers() {
		return trainers;
	}

	
}
