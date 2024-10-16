package com.epam.gym.atlass_gym.dao;

import java.util.HashMap;
import java.util.Map;

import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;

public class TrainingDAO {

	private Map<Long, Training> trainings = new HashMap<Long, Training>();
	
	public TrainingDAO() {}
	
	//create training
	public void createTraining(String trainingName,Training_type trainingType,Long trainingDuration) {
		trainings.put((long) trainings.size(),new Training(trainingName, trainingType, trainingDuration));
	}
	
	public void createTraining(Training training) {
		trainings.put((long) trainings.size(),training);
		
	}
	
	//get training
	public Training selectTraining(String trainingName) {
		return trainings.keySet().stream()
				  .filter(t -> trainings.get(t).getTrainingName().equals(trainingName))
				  .findFirst().map(k -> trainings.get(k))
				  .get();
	}

	private Long selectSimilarTrainings(String trainingName) {
		return trainings.keySet().stream()
				.filter(k -> trainings.get(k).getTrainingName().matches(trainingName))
				.count();
	}

	//get training map
	public Map<Long, Training> selectTrainings() {
		Map<Long, Training> temp_map = new HashMap<Long, Training>();
		temp_map.putAll(trainings);
		return temp_map;
	}
}
