package com.epam.gym.atlass_gym.service;

import com.epam.gym.atlass_gym.dao.TraineeDAO;
import com.epam.gym.atlass_gym.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TraineeService {

    @Autowired
    private TraineeDAO traineeDAO;

    private Logger logger = LoggerFactory.getLogger(TraineeService.class);

    //create trainee
    public Trainee createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        logger.info("Trainee " + firstName + " " + lastName + " created");
        return traineeDAO.createTrainee(firstName, lastName, dateOfBirth, address);
    }

    public void createTrainee(Trainee trainee) {
        traineeDAO.createTrainee(trainee);
        logger.info("Trainee " + trainee.getUsername() + " created");
    }

    //update trainee
    public void updateTrainee(String username, String newFirstName, String newLastName, LocalDate newDateOfBirth, String newAddress) {
        traineeDAO.updateTrainee(username, newFirstName, newLastName, newDateOfBirth, newAddress);
        logger.info("Trainee " + username + " updated");
    }

    //remove trainee
    public void deleteTrainee(String username) {
        traineeDAO.deleteTrainee(username);
        logger.info("Trainee " + username + " deleted");
    }

    //get trainee
    public Trainee selectTrainee(String username) {
        logger.info("Trainee " + username + " selected");
        return traineeDAO.selectTrainee(username);
    }


}
