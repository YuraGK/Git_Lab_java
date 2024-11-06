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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
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

    @GetMapping()
    public ResponseEntity login(@RequestBody LoginPassword loginPassword) {
        if (loginPassword == null || loginPassword.getLogin() == null || loginPassword.getPassword() == null) {
            logger.warn("Insufficient data, missing login or password");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        System.out.println(loginPassword.getLogin() + " " + loginPassword.getPassword());
        if (traineeRepository.authorise(loginPassword.getLogin(), loginPassword.getPassword())) {
            return new ResponseEntity(HttpStatus.OK);
        } else if (trainerRepository.authorise(loginPassword.getLogin(), loginPassword.getPassword())) {
            traineeRepository.setTrainer();
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PutMapping(value = "/change")
    public ResponseEntity changeLogin(@RequestBody LoginPasswordChange loginPassword) {
        if (loginPassword == null || loginPassword.getLogin() == null || loginPassword.getPassword() == null || loginPassword.getNewPassword() == null) {
            logger.warn("Insufficient data, missing login or password");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            System.out.println("Login tainee");
            traineeService.selectTrainee(loginPassword.getLogin()).setPassword(loginPassword.getNewPassword());
            return traineeRepository.changePasswordByUsername(loginPassword.getLogin(), loginPassword.getNewPassword()).isEmpty() ? new ResponseEntity(HttpStatus.FORBIDDEN) : new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            try {
                System.out.println("Login tainer");
                trainerService.selectTrainer(loginPassword.getLogin()).setPassword(loginPassword.getNewPassword());
                return trainerRepository.changePasswordByUsername(loginPassword.getLogin(), loginPassword.getNewPassword()).isEmpty() ? new ResponseEntity(HttpStatus.FORBIDDEN) : new ResponseEntity(HttpStatus.OK);
            } catch (Exception ex) {

            }
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
