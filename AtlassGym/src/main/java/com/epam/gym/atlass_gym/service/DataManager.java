 	package com.epam.gym.atlass_gym.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.gym.atlass_gym.dao.TraineeDAO;
import com.epam.gym.atlass_gym.dao.TrainerDAO;
import com.epam.gym.atlass_gym.dao.TrainingDAO;
import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;

public class DataManager {
	
	private static final String TRAINEE_DATA_FILE = "traineeData.txt";
	private static final String TRAINER_DATA_FILE = "trainerData.txt";
	private static final String TRAINING_DATA_FILE = "trainingData.txt";
	
	@Autowired
	private TrainingDAO trainingDAO;
	
	@Autowired
	private TrainerDAO trainerDAO;
	
	@Autowired
	private TraineeDAO traineeDAO;
	
	
	private Logger logger = LoggerFactory.getLogger(DataManager.class);

	
	public boolean saveTraineeData() {
		
		List<String> data = new LinkedList<>();
		
		for(Long id : traineeDAO.selectTrainees().keySet()) {
			data.add(id+" "+getTraineeData(traineeDAO.selectTrainees().get(id)));
		}
		
		try (PrintWriter out = new PrintWriter(TRAINEE_DATA_FILE)) {
		    out.println(String.join("\n", data));
		    logger.info("trainee data saved");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean saveTrainerData() {
		
		List<String> data = new LinkedList<>();
		
		for(Long id : trainerDAO.selectTrainers().keySet()) {
			data.add(id+" "+getTrainerData(trainerDAO.selectTrainers().get(id)));
		}
		
		try (PrintWriter out = new PrintWriter(TRAINER_DATA_FILE)) {
		    out.println(String.join("\n", data));
		    logger.info("trainer data saved");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean saveTrainingData() {
	
		List<String> data = new LinkedList<>();
	
		for(Long id : trainingDAO.selectTrainings().keySet()) {
			data.add(id+" "+trainingDAO.selectTrainings().get(id).toString());
		}
	
		try (PrintWriter out = new PrintWriter(TRAINING_DATA_FILE)) {
			out.println(String.join("\n", data));
			logger.info("training data saved");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean saveAllData() {
		return saveTrainingData()&&saveTrainerData()&&saveTraineeData();
	}
	
	public boolean loadAllData() {
		try {
			Scanner myReader = new Scanner(new File(TRAINEE_DATA_FILE));
			while (myReader.hasNextLine()) {
				String[] data = myReader.nextLine().split(" ");
				Trainee trainee = new Trainee(data[1], data[2], data[1]+"."+data[2], LocalDateTime.parse(data[3]), data[4], Long.parseLong(data[0]));
				traineeDAO.createTrainee(trainee);
			}
			myReader.close();
			logger.info("trainee data loaded");
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			logger.error(e.getMessage());
			return false;
		}
		
		try {
			Scanner myReader = new Scanner(new File(TRAINER_DATA_FILE));
			while (myReader.hasNextLine()) {
				String[] data = myReader.nextLine().split(" ");
				Trainer trainer = new Trainer(data[1], data[2], data[1]+"."+data[2], new Training_type(data[3]), Long.parseLong(data[0]));
				trainerDAO.createTrainer(trainer);
			}
			myReader.close();
			logger.info("trainer data loaded");
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			logger.error(e.getMessage());
			return false;
		}
		
		try {
			Scanner myReader = new Scanner(new File(TRAINING_DATA_FILE));
			while (myReader.hasNextLine()) {
				String[] data = myReader.nextLine().split(" ");
				Training training = new Training(Arrays.asList(data[1].split(":")), Arrays.asList(data[2].split(":")),data[3],new Training_type(data[4]),Long.parseLong(data[5]));
				trainingDAO.createTraining(training);
			}
			myReader.close();
			logger.info("training data loaded");
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	private String getTraineeData(Trainee trainee) {
		return trainee.getFirstName()+" "+trainee.getLastName()+" "+trainee.getDateOfBirth().toString()+" "+trainee.getAddress()+" "+trainee.getUserId();
	}
	
	private String getTrainerData(Trainer trainer) {
		return trainer.getFirstName()+" "+trainer.getLastName()+" "+trainer.getSpecialisation().getTraining_type()+" "+trainer.getUserId();
	}
}
