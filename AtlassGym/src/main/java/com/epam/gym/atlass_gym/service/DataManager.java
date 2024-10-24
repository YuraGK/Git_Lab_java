package com.epam.gym.atlass_gym.service;

import com.epam.gym.atlass_gym.dao.TraineeDAO;
import com.epam.gym.atlass_gym.dao.TrainerDAO;
import com.epam.gym.atlass_gym.dao.TrainingDAO;
import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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

        for (Long id : traineeDAO.selectTrainees().keySet()) {
            data.add(id + " " + getTraineeData(traineeDAO.selectTrainees().get(id)));
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

        for (Long id : trainerDAO.selectTrainers().keySet()) {
            data.add(id + " " + getTrainerData(trainerDAO.selectTrainers().get(id)));
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

        for (Long id : trainingDAO.selectTrainings().keySet()) {
            data.add(id + " " + trainingDAO.selectTrainings().get(id).toString());
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
        return saveTrainingData() && saveTrainerData() && saveTraineeData();
    }

    public boolean loadAllData() {
        try {
            Scanner myReader = new Scanner(new File(TRAINEE_DATA_FILE));
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(" ");
                Trainee trainee = new Trainee(data[1], data[2], data[1] + "." + data[2], LocalDate.parse(data[3]), data[4], Long.parseLong(data[0]));
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
                Trainer trainer = new Trainer(data[1], data[2], data[1] + "." + data[2], new Training_type(data[3]), Long.parseLong(data[0]));
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
                //0 1   A light 2000-10-14 15
                Training training = new Training(data[4], new Training_type(data[5]), LocalDate.parse(data[6]), Long.parseLong(data[7]));
                if (!data[2].equals("")) {
                    for (Long num : traineeDAO.selectTrainees().keySet()) {
                        if (traineeDAO.selectTrainees().get(num).getId() == Long.parseLong(data[2])) {
                            training.setTraineeIds(traineeDAO.selectTrainees().get(num));
                            break;
                        }
                    }
                }
                if (!data[3].equals("")) {
                    for (Long num : trainerDAO.selectTrainers().keySet()) {
                        if (trainerDAO.selectTrainers().get(num).getId() == Long.parseLong(data[3])) {
                            training.setTrainerIds(trainerDAO.selectTrainers().get(num));
                            break;
                        }
                    }
                }
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
        return trainee.getFirstName() + " " + trainee.getLastName() + " " + trainee.getDateOfBirth().toString() + " " + trainee.getAddress() + " " + trainee.getId();
    }

    private String getTrainerData(Trainer trainer) {
        return trainer.getFirstName() + " " + trainer.getLastName() + " " + trainer.getSpecialisation().getTraining_type() + " " + trainer.getId();
    }
}
