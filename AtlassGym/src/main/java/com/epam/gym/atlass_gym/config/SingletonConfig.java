package com.epam.gym.atlass_gym.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.gym.atlass_gym.dao.TraineeDAO;
import com.epam.gym.atlass_gym.dao.TrainerDAO;
import com.epam.gym.atlass_gym.dao.TrainingDAO;
import com.epam.gym.atlass_gym.service.DataManager;

@Configuration
public class SingletonConfig {

	@Bean
	public TrainerDAO trainerDAO() {return new TrainerDAO();}
	
	@Bean
	public TraineeDAO traineeDAO() {return new TraineeDAO();}
	
	@Bean
	public TrainingDAO trainingDAO() {return new TrainingDAO();}
	
	@Bean
	public DataManager dataManager() {return new DataManager();}
	
	
}
