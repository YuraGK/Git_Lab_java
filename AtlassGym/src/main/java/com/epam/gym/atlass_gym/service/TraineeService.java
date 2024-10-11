package com.epam.gym.atlass_gym.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.gym.atlass_gym.dao.TraineeDAO;
import com.epam.gym.atlass_gym.model.Trainee;

@Service
public class TraineeService {
	
	@Autowired
	private TraineeDAO traineeDAO;
	
	@Autowired
	private Logger logger;

	//create trainee
	public void createTrainee(String firstName, String lastName, LocalDateTime dateOfBirth, String address) {
		traineeDAO.createTrainee(firstName, lastName, dateOfBirth, address);
		logger.info("Trainee "+firstName+" "+lastName+" created");
	}
	
	public void createTrainee(Trainee trainee) {
		traineeDAO.createTrainee(trainee);
		logger.info("Trainee "+trainee.getUsername()+" created");
	}
	
	//update trainee
	public void updateTrainee(String username, String newFirstName, String newLastName, LocalDateTime newDateOfBirth, String newAddress) {
		traineeDAO.updateTrainee(username, newFirstName, newLastName, newDateOfBirth, newAddress);
		logger.info("Trainee "+username+" updated");
	}
	
	//remove trainee
	public void deleteTrainee(String username) {
		traineeDAO.deleteTrainee(username);
		logger.info("Trainee "+username+" deleted");
	}
	
	//get trainee
	public Trainee selectTrainee(String username) {
		logger.info("Trainee "+username+" selected");
		return traineeDAO.selectTrainee(username);
	}
}
