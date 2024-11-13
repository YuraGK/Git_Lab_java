package com.epam.gym.atlass_gym.controller;


import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.mapped.SimpleTraining;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainingRepositoryImpl;
import com.epam.gym.atlass_gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/gym/training", consumes = {"application/JSON"})
public class TrainingController {

    @Autowired
    private TrainingRepositoryImpl trainingRepository;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TraineeRepositoryImpl traineeRepository;
    @Autowired
    private TrainerRepositoryImpl trainerRepository;
    private Logger logger = LoggerFactory.getLogger(TrainingController.class);

    @PostMapping(value = "/add")
    public String add(@RequestBody SimpleTraining training) {

        if (training == null ||
                training.getTrainer() == null ||
                training.getTrainee() == null ||
                training.getTrainingName() == null ||
                training.getTrainingDate() == null ||
                training.getTrainingDuration() == null) {
            logger.warn("Insufficient data, missing training name, trainer or trainee, training date, duration");
            return "400";
        }
        if (traineeRepository.getTraineeByUsername(training.getTrainee()) == null) {
            logger.warn("Trying to use non-existent trainee");
            return "403";
        }
        if (trainerRepository.getTrainerByUsername(training.getTrainer()) == null) {
            logger.warn("Trying to use non-existent trainer");
            return "403";
        }
        Trainee trainee = traineeRepository.getTraineeByUsername(training.getTrainee());
        Trainer trainer = trainerRepository.getTrainerByUsername(training.getTrainer());

        Training train = trainingService.createTraining(training.getTrainingName(), trainee, trainer, training.getTrainingDate(), training.getTrainingDuration());

        System.out.println(train.getId());
        System.out.println(train.getTrainerIds().toString());
        System.out.println(train.getTraineeIds().toString());
        System.out.println(train.getTrainingName());
        System.out.println(train.getTrainingDate());
        System.out.println(train.getTrainingDuration());
        System.out.println(train.getTrainingType().getTraining_type());
        if (trainingRepository.save(train).isEmpty()) {
            logger.warn("Error trying to save training");
            return "403";
        }

        return "index";
    }

    @GetMapping(value = "/getTypes")
    public String getProfile(Model model) {
        model.addAttribute("types", trainingRepository.getTrainingTypes());
        return "index";
    }

}
