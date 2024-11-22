package com.epam.gym.atlass_gym.dao;

import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.Training_type;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TrainingDAO {

    private Map<Long, Training> trainings = new HashMap<Long, Training>();

    public TrainingDAO() {
    }

    //create training
    public Training createTraining(String trainingName, Trainee trainee, Trainer trainer, LocalDate trainingDate, Long trainingDuration) {
        Long newId = (trainings.size()) + 1L;
        System.out.println(newId + "+" + trainings.size());
        Training t = new Training(trainee, trainer, trainingName, trainer.getSpecialisation(), trainingDate, trainingDuration, newId);
        trainings.put((long) trainings.size(), t);
        return t;
    }

    public Training createTraining(String trainingName, Training_type trainingType, LocalDate trainingDate, Long trainingDuration) {
        Long newId = (trainings.size()) + 1L;
        System.out.println(newId + "+" + trainings.size());
        Training t = new Training(trainingName, trainingType, trainingDate, trainingDuration, newId);
        trainings.put((long) trainings.size(), t);
        return t;
    }

    public void createTraining(Training training) {
        Long newId = (trainings.size()) + 1L;
        System.out.println(newId + "+" + trainings.size());
        training.setId(newId);
        trainings.put((long) trainings.size(), training);
    }

    //get training
    public Training selectTraining(String trainingName) {
        return trainings.keySet().stream()
                .filter(t -> trainings.get(t).getTrainingName().equals(trainingName))
                .findFirst().map(k -> trainings.get(k))
                .get();
    }

    //get training map
    public Map<Long, Training> selectTrainings() {
        Map<Long, Training> temp_map = new HashMap<Long, Training>();
        temp_map.putAll(trainings);
        return temp_map;
    }

    public void dropTrainings() {
        trainings = new HashMap<Long, Training>();
    }
}
