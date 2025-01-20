package com.epam.gym.atlass_gym.controller;


import com.epam.gym.atlass_gym.model.Trainee;
import com.epam.gym.atlass_gym.model.Trainer;
import com.epam.gym.atlass_gym.model.Training;
import com.epam.gym.atlass_gym.model.mapped.SimpleTraining;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainingRepositoryImpl;
import com.epam.gym.atlass_gym.service.JWTService;
import com.epam.gym.atlass_gym.service.TrainingService;
import com.netflix.discovery.EurekaClient;
import com.warehouse.tmp.module.WorkloadInput;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/training", consumes = {"application/JSON"})
public class TrainingController {


    @Autowired
    @Lazy
    private EurekaClient eurekaClient;
    @Autowired
    private TrainingRepositoryImpl trainingRepository;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TraineeRepositoryImpl traineeRepository;
    @Autowired
    private TrainerRepositoryImpl trainerRepository;
    private Logger logger = LoggerFactory.getLogger(TrainingController.class);
    @Autowired
    private JWTService jwtService;
    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private Sender senderService;

    @PostMapping(value = "/add")
    @CircuitBreaker(name = "putTraining", fallbackMethod = "fallbackPutTraining")
    public String add(@RequestBody SimpleTraining training, Model model) {

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

        //send to workload service

        WorkloadInput workloadInput = new WorkloadInput(
                trainer.getUsername(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.isActive(),
                train.getTrainingDate(),
                train.getTrainingDuration(),
                "ADD");


        //Workload workload = trainingService.sendWorkloadInfo(workloadInput);

/*
        if (workload == null) {
            logger.warn("Error trying to save workload");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("workload", workload);
*/
        model.addAttribute("workload", senderService.sendMessage(workloadInput));
        return "index";
    }


    @GetMapping(value = "/getTypes")
    public String getProfile(Model model) {
        model.addAttribute("types", trainingRepository.getTrainingTypes());
        return "index";
    }

    @GetMapping(value = "/getWorkloadReport", produces = {"application/JSON"}, consumes = {"application/JSON"})
    @CircuitBreaker(name = "getReport", fallbackMethod = "fallbackGetReport")
    public String getWorkloadReport(Model model) {
        /*TrainersMonthlyTrainings report = trainingService.getWorkloadInfo();
        if (report == null) {
            logger.warn("Error trying to get report");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("report", report);*/


        model.addAttribute("report", senderService.sendMessage("doit"));
        return "index";
    }
/*
    @JmsListener(destination = "getreport")
    public TrainersMonthlyTrainings getTrainersMonthlySummary(final Message message) throws Exception {
        TrainersMonthlyTrainings report = (TrainersMonthlyTrainings) messageConverter.fromMessage(message);
        return report;
    }*/


    public ResponseEntity fallbackPutTraining(Throwable throwable) {
        logger.error("Fallback on PutTraining: ", throwable.getMessage(), throwable);
        return new ResponseEntity<>("Fail to create training ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity fallbackGetReport(Throwable throwable) {
        logger.error("Fallback on GetReport: ", throwable.getMessage(), throwable);
        return new ResponseEntity<>("Fail to get report ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
