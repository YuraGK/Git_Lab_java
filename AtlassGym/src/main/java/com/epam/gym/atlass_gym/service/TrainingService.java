package com.epam.gym.atlass_gym.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.gym.atlass_gym.dao.TrainingDAO;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;

@Service
public class TrainingService {

	@Autowired
	private TrainingDAO trainingDAO;
	
	
	private Logger logger = LoggerFactory.getLogger(TrainingService.class);

	public TrainingService() {}
	
	
	public void createTraining(Training training) {
		trainingDAO.createTraining(training);
		logger.info("Training "+training.getTrainingName()+" created");
	}
	public void createTraining(String trainingName,Training_type trainingType,Long trainingDuration) {
		trainingDAO.createTraining(trainingName, trainingType, trainingDuration);
		logger.info("Training "+trainingName+" created");
	}
	public Training selectTraining(String trainingName) {
		logger.info("Training "+trainingName+" selected");
		return trainingDAO.selectTraining(trainingName);
	}
}
