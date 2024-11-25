package com.epam.gym.atlass_gym.controller;

import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.mapped.ActiveUser;
import com.epam.gym.atlass_gym.model.mapped.SimpleTrainee;
import com.epam.gym.atlass_gym.model.mapped.TraineesTrainersList;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainingRepositoryImpl;
import com.epam.gym.atlass_gym.service.JWTService;
import com.epam.gym.atlass_gym.service.TraineeService;
import com.epam.gym.atlass_gym.service.TrainerService;
import com.epam.gym.atlass_gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Controller
@RequestMapping(value = "/gym/trainee", consumes = {"application/JSON"})
public class TraineeController {

    @Autowired
    private TraineeService traineeService;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TraineeRepositoryImpl traineeRepository;
    @Autowired
    private TrainerRepositoryImpl trainerRepository;
    @Autowired
    private TrainingRepositoryImpl trainingRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;

    private Logger logger = LoggerFactory.getLogger(TraineeController.class);

    @PostMapping(value = "/register")
    public String register(@RequestBody Trainee trainee, Model model) {
        if (trainee == null || trainee.getFirstName() == null || trainee.getLastName() == null) {
            logger.warn("Insufficient data, missing first or last name");
            return null;
        }
        Trainee outTrainee = traineeService.createTrainee(trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getDateOfBirth(),
                trainee.getAddress());
        model.addAttribute("username", outTrainee.getUsername());
        model.addAttribute("password", outTrainee.getPassword());

        System.out.println(outTrainee.getPassword());

        outTrainee.setPassword(passwordEncoder.encode(outTrainee.getPassword()));
        traineeRepository.save(outTrainee);
        return "index";
    }

    @GetMapping(value = "/getProfile")
    public String getProfile(@RequestBody String username, Model model) {
        System.out.println(username);
        Trainee trainee = null;
        try {
            trainee = traineeService.selectTrainee(username);
        } catch (NoSuchElementException e) {
            trainee = traineeRepository.getTraineeByUsername(username);
        }
        model.addAttribute("trainee", trainee);
        return "index";
    }

    @PutMapping(value = "/updateProfile")
    public String update(@RequestBody SimpleTrainee newTraineeInfo, Model model) {
        if (newTraineeInfo == null || newTraineeInfo.getUsername() == null || newTraineeInfo.getLastName() == null || newTraineeInfo.getFirstName() == null) {
            logger.warn("Insufficient data, missing username, first or last name");
            return null;
        }
        if (traineeRepository.getTraineeByUsername(newTraineeInfo.getUsername()) == null) {
            logger.warn("Trying to update non-existent trainee");
            return null;
        }
        Trainee t = new Trainee();
        try {

            t = traineeService.selectTrainee(newTraineeInfo.getUsername());
            traineeService.updateTrainee(newTraineeInfo.getUsername(),
                    newTraineeInfo.getFirstName(),
                    newTraineeInfo.getLastName(),
                    (newTraineeInfo.getDateOfBirth() == null) ? t.getDateOfBirth() : newTraineeInfo.getDateOfBirth(),
                    (newTraineeInfo.getAddress() == null) ? t.getAddress() : newTraineeInfo.getAddress());
            traineeService.selectTrainee(newTraineeInfo.getUsername()).setActive(newTraineeInfo.isActive());
            t = traineeService.selectTrainee(newTraineeInfo.getUsername());
        } catch (Exception e) {
            t = traineeRepository.getTraineeByUsername(newTraineeInfo.getUsername());
            t.setFirstName(newTraineeInfo.getFirstName());
            t.setLastName(newTraineeInfo.getLastName());
            t.setAddress((newTraineeInfo.getAddress() == null) ? t.getAddress() : newTraineeInfo.getAddress());
            t.setDateOfBirth((newTraineeInfo.getDateOfBirth() == null) ? t.getDateOfBirth() : newTraineeInfo.getDateOfBirth());
            t.setActive(newTraineeInfo.isActive());
        }
        traineeRepository.save(t);
        model.addAttribute("trainee", t);

        return "index";
    }

    @DeleteMapping(value = "/deleteProfile")
    public String delete(@RequestBody String username) {
        try {
            traineeService.deleteTrainee(username);
        } catch (Exception e) {

        }
        if (traineeRepository.getTraineeByUsername(username) == null) {
            return "400";
        }
        traineeRepository.deleteTrainee(traineeRepository.getTraineeByUsername(username));
        return "index";
    }

    @GetMapping(value = "/getAvailableTrainers")
    public String getAvailableTrainers(@RequestBody String traineeUsername, Model model) {
        System.out.println(traineeUsername);
        model.addAttribute("trainers", trainerRepository.getAvailableTrainerListByTraineeUsername(traineeUsername));
        return "index";
    }

    @PutMapping(value = "/updateTraineesTrainersList")
    public String updateTraineesTrainersList(@RequestBody TraineesTrainersList traineesTrainersList, Model model) {
        System.out.println(traineesTrainersList.getTrainers());
        if (traineesTrainersList == null || traineesTrainersList.getLogin() == null || traineesTrainersList.getTrainers() == null) {
            logger.warn("Insufficient data, missing username or trainers list");
            return "404";
        }

        if (traineeRepository.getTraineeByUsername(traineesTrainersList.getLogin()) == null) {
            logger.warn("Trying to update non-existent trainee");
            return "404";
        }
        Trainee t = new Trainee();
        try {
            traineeService.selectTrainee(traineesTrainersList.getLogin()).setTrainers(Arrays.stream(traineesTrainersList.getTrainers()).toList());
            t = traineeService.selectTrainee(traineesTrainersList.getLogin());
            traineeRepository.save(t);
        } catch (Exception e) {
            //
            t = traineeRepository.getTraineeByUsername(traineesTrainersList.getLogin());
            t.setTrainers(Arrays.stream(traineesTrainersList.getTrainers()).toList());
            traineeRepository.save(t);
        }
        model.addAttribute("trainers", traineesTrainersList.getTrainers());
        return "index";
    }

    @GetMapping(value = "/getTraineesTrainingsList")
    public String getTraineesTrainingsList(@RequestBody String traineeUsername, Model model) {
        if (traineeRepository.getTraineeByUsername(traineeUsername) == null) {
            logger.warn("Trying to update non-existent trainee");
            return "404";
        }
        model.addAttribute("trainings", traineeRepository.getTrainingsByUsernameAndCriteria(traineeUsername, null, null, null, null));
        return "index";
    }

    @PatchMapping(value = "/toggleActive")
    public String toggleActive(@RequestBody ActiveUser user) {
        System.out.println(user.getUsername() + " " + user.isActive());
        if (user == null || user.getUsername() == null) {
            logger.warn("Insufficient data, missing username");
            return "404";
        }
        if (traineeRepository.getTraineeByUsername(user.getUsername()) == null) {
            logger.warn("Trying to update non-existent trainee");
            return "404";
        }
        boolean userIsActive = traineeRepository.getTraineeByUsername(user.getUsername()).isActive();
        if (user.isActive() != userIsActive) {
            traineeRepository.toggleActiveByUsername(user.getUsername());
        }
        return "index";
    }
}
