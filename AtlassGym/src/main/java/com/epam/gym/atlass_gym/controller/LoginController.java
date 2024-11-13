package com.epam.gym.atlass_gym.controller;

import com.epam.gym.atlass_gym.model.mapped.LoginPassword;
import com.epam.gym.atlass_gym.model.mapped.LoginPasswordChange;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.service.TraineeService;
import com.epam.gym.atlass_gym.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private TraineeService traineeService;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TraineeRepositoryImpl traineeRepository;
    @Autowired
    private TrainerRepositoryImpl trainerRepository;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping(value = "/login", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    public String login(@RequestBody LoginPassword loginPassword) {
        if (loginPassword == null || loginPassword.getLogin() == null || loginPassword.getPassword() == null) {
            logger.warn("Insufficient data, missing login or password");
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return "400";
        }
        System.out.println(loginPassword.getLogin() + " " + loginPassword.getPassword());
        if (traineeRepository.authorise(loginPassword.getLogin(), loginPassword.getPassword())) {
            logger.info("Logged as trainee");
            return "index";
        } else if (trainerRepository.authorise(loginPassword.getLogin(), loginPassword.getPassword())) {
            traineeRepository.setTrainer();
            logger.info("Logged as trainer");
            return "index";
        }
        return "400";
    }

    @PutMapping(value = "/login/change", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    public String changeLogin(@RequestBody LoginPasswordChange loginPassword) {
        if (loginPassword == null || loginPassword.getLogin() == null || loginPassword.getPassword() == null || loginPassword.getNewPassword() == null) {
            logger.warn("Insufficient data, missing login or password");
            return "404";
        }
        System.out.println(loginPassword.getLogin() + " " + loginPassword.getPassword());
        try {
            System.out.println("Change Login trainee");
            traineeService.selectTrainee(loginPassword.getLogin()).setPassword(loginPassword.getNewPassword());
            System.out.println("Change Login trainee");
            return traineeRepository.changePasswordByUsername(loginPassword.getLogin(), loginPassword.getNewPassword()).isEmpty() ? "403" : "index";
        } catch (Exception e) {
            try {
                System.out.println("Change Login trainer");
                trainerService.selectTrainer(loginPassword.getLogin()).setPassword(loginPassword.getNewPassword());
                return trainerRepository.changePasswordByUsername(loginPassword.getLogin(), loginPassword.getNewPassword()).isEmpty() ? "403" : "index";
            } catch (Exception ex) {

            }
        }
        return "403";
    }
}
