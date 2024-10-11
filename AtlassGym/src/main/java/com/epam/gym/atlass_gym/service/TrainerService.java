package com.epam.gym.atlass_gym.service;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.gym.atlass_gym.dao.TrainerDAO;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training_type;

@Service
public class TrainerService {
	
	@Autowired
	private TrainerDAO trainerDAO;
	
	@Autowired
	private Logger logger;

	public TrainerService() {
	}
	
	//create trainer
	public void createTrainer(String firstName, String lastName, Training_type specialisation) {
		trainerDAO.createTrainer(firstName, lastName, specialisation);
		logger.info("Trainer "+firstName+" "+lastName+" created");
	}
	
	public void createTrainer(Trainer trainer) {
		trainerDAO.createTrainer(trainer);
		logger.info("Trainer "+trainer.getUsername()+" created");
	}
	
	//update trainer
	public void updateTrainer(String username, String newFirstName, String newLastName, Training_type newSpecialisation) {
		trainerDAO.updateTrainer(username, newFirstName, newLastName, newSpecialisation);
		logger.info("Trainer "+username+" updated");
	}
	
	//get trainer
	public Trainer selectTrainer(String username) {
		logger.info("Trainer "+username+" selected");
		return trainerDAO.selectTrainer(username);
	}
	
	
}
