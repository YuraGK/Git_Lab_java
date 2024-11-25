package com.epam.gym.atlass_gym.controller;

import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.mapped.ActiveUser;
import com.epam.gym.atlass_gym.model.mapped.SimpleTrainer;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainingRepositoryImpl;
import com.epam.gym.atlass_gym.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping(value = "/gym/trainer", consumes = {"application/JSON"})
public class TrainerController {

    @Autowired
    private CustomMetricService customMetricsService;
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
    private Logger logger = LoggerFactory.getLogger(TrainerController.class);
    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register")
    public String register(@RequestBody Trainer trainer, Model model) {
        trainer.setUsername(trainer.getFirstName() + "." + trainer.getLastName());
        if (trainer == null || trainer.getFirstName() == null || trainer.getLastName() == null) {
            logger.warn("Insufficient data, missing first or last name");
            return null;
        }
        Trainer outTrainer = trainerService.createTrainer(trainer.getFirstName(),
                trainer.getLastName(), trainer.getSpecialisation());


        model.addAttribute("username", outTrainer.getUsername());
        model.addAttribute("password", outTrainer.getPassword());
        outTrainer.setPassword(passwordEncoder.encode(outTrainer.getPassword()));
        trainerRepository.save(outTrainer);
        return "index";
    }

    @GetMapping(value = "/getProfile")
    public String getProfile(@RequestBody String username, Model model) {
        System.out.println(username);
        Trainer trainer = null;
        try {
            trainer = trainerService.selectTrainer(username);
        } catch (NoSuchElementException e) {
            trainer = trainerRepository.getTrainerByUsername(username);
        }
        model.addAttribute("trainer", trainer);
        return "index";
    }

    @PutMapping(value = "/updateProfile")
    public String update(@RequestBody SimpleTrainer newTrainerInfo, Model model) {
        if (newTrainerInfo == null || newTrainerInfo.getUsername() == null || newTrainerInfo.getLastName() == null || newTrainerInfo.getFirstName() == null) {
            logger.warn("Insufficient data, missing username, first or last name");
            return "404";
        }
        if (trainerRepository.getTrainerByUsername(newTrainerInfo.getUsername()) == null) {
            logger.warn("Trying to update non-existent trainee");
            return "404";
        }
        Trainer t = new Trainer();
        try {

            t = trainerService.selectTrainer(newTrainerInfo.getUsername());
            trainerService.updateTrainer(newTrainerInfo.getUsername(),
                    newTrainerInfo.getFirstName(),
                    newTrainerInfo.getLastName(),
                    (newTrainerInfo.getSpecialization() == null) ? t.getSpecialisation() : newTrainerInfo.getSpecialization()
            );
            trainerService.selectTrainer(newTrainerInfo.getUsername()).setActive(newTrainerInfo.isActive());
            t = trainerService.selectTrainer(newTrainerInfo.getUsername());
        } catch (Exception e) {
            t = trainerRepository.getTrainerByUsername(newTrainerInfo.getUsername());
            t.setFirstName(newTrainerInfo.getFirstName());
            t.setLastName(newTrainerInfo.getLastName());
            t.setSpecialisation((newTrainerInfo.getSpecialization() == null) ? t.getSpecialisation() : newTrainerInfo.getSpecialization());
            t.setActive(newTrainerInfo.isActive());
        }
        trainerRepository.save(t);
        model.addAttribute("trainer", t);
        return "index";
    }

    @GetMapping(value = "/getTrainersTrainingsList")
    public String getTrainersTrainingsList(@RequestBody String trainerUsername, Model model) {
        if (trainerRepository.getTrainerByUsername(trainerUsername) == null) {
            logger.warn("Trying to update non-existent trainee");
            return "404";
        }
        model.addAttribute("trainings", traineeRepository.getTrainingsByUsernameAndCriteria(trainerUsername, null, null, null, null));
        return "index";
    }

    @PatchMapping(value = "/toggleActive")
    public String toggleActive(@RequestBody ActiveUser user) {
        System.out.println(user.getUsername() + " " + user.isActive());
        if (user == null || user.getUsername() == null) {
            logger.warn("Insufficient data, missing username");
            return "403";
        }
        if (trainerRepository.getTrainerByUsername(user.getUsername()) == null) {
            logger.warn("Trying to update non-existent trainer");
            return "403";
        }
        boolean userIsActive = trainerRepository.getTrainerByUsername(user.getUsername()).isActive();
        if (user.isActive() != userIsActive) {
            trainerRepository.toggleActiveByUsername(user.getUsername());
        }
        return "index";
    }
}
