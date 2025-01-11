package com.epam.gym.atlass_gym.controller;

import com.epam.gym.atlass_gym.model.mapped.LoginPassword;
import com.epam.gym.atlass_gym.model.mapped.LoginPasswordChange;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.service.BruteForceService;
import com.epam.gym.atlass_gym.service.JWTService;
import com.epam.gym.atlass_gym.service.TraineeService;
import com.epam.gym.atlass_gym.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping
public class LoginController {
    @Autowired
    private BruteForceService bruteForceService;
    @Autowired
    private TraineeService traineeService;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TraineeRepositoryImpl traineeRepository;
    @Autowired
    private TrainerRepositoryImpl trainerRepository;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }
/*
    @GetMapping(value = "/login")
    public String getLogin() {
        return "login";
    }*/

    @GetMapping(value = "/logout", consumes = {"application/JSON"})
    public String getLogout() {
        traineeRepository.logout();
        return "index";
    }

    @GetMapping(value = "/login", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    public String login(@RequestBody LoginPassword loginPassword, Model model) {
        if (loginPassword == null || loginPassword.getLogin() == null || loginPassword.getPassword() == null) {
            logger.warn("Insufficient data, missing login or password");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            //return "400";
        }
        String login = loginPassword.getLogin();
        if (bruteForceService.isBlocked(login)) throw new ResponseStatusException(HttpStatus.LOCKED);
        System.out.println(login + " " + loginPassword.getPassword());
        if (traineeRepository.authorise(login, loginPassword.getPassword())) {
            bruteForceService.resetFailedLoginAttempts(login);
            logger.info("Logged as trainee");
            model.addAttribute("token", jwtService.generateToken(login));
            return "index";
        } else if (trainerRepository.authorise(login, loginPassword.getPassword())) {
            traineeRepository.setTrainer();
            bruteForceService.resetFailedLoginAttempts(login);
            model.addAttribute("token", jwtService.generateToken(login));
            logger.info("Logged as trainer");
            return "index";
        }
        bruteForceService.recordFailedLogin(login);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        //return "400";
    }

    @PutMapping(value = "/login/change", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    public String changeLogin(@RequestBody LoginPasswordChange loginPassword) {
        if (loginPassword == null || loginPassword.getLogin() == null || loginPassword.getPassword() == null || loginPassword.getNewPassword() == null) {
            logger.warn("Insufficient data, missing login or password");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            //return "404";
        }
        System.out.println(loginPassword.getLogin() + " " + loginPassword.getPassword());
        try {
            System.out.println("Change Login trainee");
            traineeService.selectTrainee(loginPassword.getLogin()).setPassword(loginPassword.getNewPassword());
            return traineeRepository.changePasswordByUsername(loginPassword.getLogin(), passwordEncoder.encode(loginPassword.getNewPassword())).isEmpty() ? "403" : "index";
        } catch (Exception e) {
            try {
                System.out.println("Change Login trainer");
                trainerService.selectTrainer(loginPassword.getLogin()).setPassword(passwordEncoder.encode(loginPassword.getNewPassword()));

                return trainerRepository.changePasswordByUsername(loginPassword.getLogin(), passwordEncoder.encode(loginPassword.getNewPassword())).isEmpty() ? "403" : "index";
            } catch (Exception ex) {
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        //return "403";
    }
}
