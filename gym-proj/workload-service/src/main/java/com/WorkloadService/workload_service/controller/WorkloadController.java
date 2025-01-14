package com.WorkloadService.workload_service.controller;


import com.WorkloadService.workload_service.model.TrainersMonthlyTrainings;
import com.WorkloadService.workload_service.model.Workload;
import com.WorkloadService.workload_service.model.WorkloadInput;
import com.WorkloadService.workload_service.service.WorkloadService;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(consumes = {"application/JSON"})
@RequiredArgsConstructor
public class WorkloadController {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    private final WorkloadService workloadService;
    private Logger logger = LoggerFactory.getLogger(WorkloadController.class);

    @PutMapping(value = "/putWorkload")
    public Workload putWorkload(@RequestBody WorkloadInput workload, Model model) {
        System.out.println(workload.getUsername() + " " + workload.isActive());
        if (workload == null || workload.getUsername() == null) {
            logger.warn("Insufficient data, missing username");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Workload result = workloadService.createMonthlyTraining(workload);


        return result;
    }

    @GetMapping(value = "/getWorkload", consumes = {"application/JSON"}, produces = {"application/JSON"})
    public TrainersMonthlyTrainings getTrainersMonthlySummary(Model model) {

        TrainersMonthlyTrainings report = workloadService.getTrainersMonthlySummary();
        return report;
    }

}
