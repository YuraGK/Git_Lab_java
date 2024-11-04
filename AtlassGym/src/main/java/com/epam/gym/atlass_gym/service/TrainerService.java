package com.epam.gym.atlass_gym.service;

import com.epam.gym.atlass_gym.dao.TrainerDAO;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training_type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {

    @Autowired
    private TrainerDAO trainerDAO;

    private Logger logger = LoggerFactory.getLogger(TrainerService.class);

    public TrainerService() {
    }

    //create trainer
    public Trainer createTrainer(String firstName, String lastName, Training_type specialisation) {
        logger.info("Trainer " + firstName + " " + lastName + " created");
        return trainerDAO.createTrainer(firstName, lastName, specialisation);
    }

    public void createTrainer(Trainer trainer) {
        trainerDAO.createTrainer(trainer);
        logger.info("Trainer " + trainer.getUsername() + " created");
    }

    //update trainer
    public void updateTrainer(String username, String newFirstName, String newLastName, Training_type newSpecialisation) {
        trainerDAO.updateTrainer(username, newFirstName, newLastName, newSpecialisation);
        logger.info("Trainer " + username + " updated");
    }

    //get trainer
    public Trainer selectTrainer(String username) {
        logger.info("Trainer " + username + " selected");
        return trainerDAO.selectTrainer(username);
    }


}
