package com.epam.gym.atlass_gym.dao;

import com.epam.gym.atlass_gym.model.Trainee;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TraineeDAO {

    private Map<Long, Trainee> trainees = new HashMap<Long, Trainee>();

    public TraineeDAO() {
    }

    //create trainee
    public void createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        Long newId = 1L;
        if (trainees.size() > 0) {
            newId = trainees.get(trainees.keySet().stream().max(Comparator.naturalOrder()).get() - 1).getId() + 1;
        }
        Long num = selectSimilarTrainees(firstName + "." + lastName);
        trainees.put(newId, new Trainee(firstName, lastName, firstName + "." + lastName + ((num > 0) ? (num) : ("")), dateOfBirth, address, newId));
    }

    public void createTrainee(Trainee trainee) {
        trainees.put(trainee.getId(), trainee);
    }

    //update trainee
    public void updateTrainee(String username, String newFirstName, String newLastName, LocalDate newDateOfBirth, String newAddress) {
        Trainee t = selectTrainee(username);
        t.setFirstName(newFirstName);
        t.setLastName(newLastName);
        t.setDateOfBirth(newDateOfBirth);
        t.setAddress(newAddress);
    }

    //remove trainee
    public void deleteTrainee(String username) {
        trainees.remove(selectTrainee(username).getId());
    }


    //get trainee
    public Trainee selectTrainee(String username) {
        return trainees.keySet().stream()
                .filter(k -> trainees.get(k).getUsername().equals(username))
                .findFirst().map(k -> trainees.get(k))
                .get();
    }

    //get similar trainees num
    private Long selectSimilarTrainees(String username) {
        return trainees.keySet().stream()
                .filter(k -> trainees.get(k).getUsername().matches(username))
                .count();
    }

    //get trainee map
    public Map<Long, Trainee> selectTrainees() {
        Map<Long, Trainee> temp_map = new HashMap<Long, Trainee>();
        temp_map.putAll(trainees);
        return temp_map;
    }


}
