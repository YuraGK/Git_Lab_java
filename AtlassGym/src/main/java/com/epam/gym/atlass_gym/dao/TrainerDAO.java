package com.epam.gym.atlass_gym.dao;

import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training_type;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TrainerDAO {

    private Map<Long, Trainer> trainers = new HashMap<Long, Trainer>();

    public TrainerDAO() {
    }

    //create trainer
    public void createTrainer(String firstName, String lastName, Training_type specialisation) {
        Long newId = 1L;
        if (trainers.size() > 0) {
            newId = trainers.get(trainers.keySet().stream().max(Comparator.naturalOrder()).get() - 1).getId() + 1;
        }

        Long num = selectSimilarTrainers(firstName + "." + lastName);

        trainers.put(newId, new Trainer(firstName, lastName, firstName + "." + lastName + ((num > 0) ? (num) : ("")), specialisation, newId));
    }

    public void createTrainer(Trainer trainer) {
        trainers.put(trainer.getId(), trainer);
    }

    //update trainer
    public void updateTrainer(String username, String newFirstName, String newLastName, Training_type newSpecialisation) {
        Trainer t = selectTrainer(username);
        t.setFirstName(newFirstName);
        t.setLastName(newLastName);
        t.setSpecialisation(newSpecialisation);

    }

    //get trainer
    public Trainer selectTrainer(String username) {
        return trainers.keySet().stream()
                .filter(k -> trainers.get(k).getUsername().equals(username))
                .findFirst().map(k -> trainers.get(k))
                .get();
    }

    //get similar trainers num
    private Long selectSimilarTrainers(String username) {
        return trainers.keySet().stream()
                .filter(k -> trainers.get(k).getUsername().matches(username))
                .count();
    }

    //get trainer map
    public Map<Long, Trainer> selectTrainers() {
        Map<Long, Trainer> temp_map = new HashMap<Long, Trainer>();
        temp_map.putAll(trainers);
        return temp_map;
    }
}
