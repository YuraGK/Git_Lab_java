package com.WorkloadService.workload_service.controller;


import com.WorkloadService.workload_service.model.TrainersMonthlyTrainings;
import com.WorkloadService.workload_service.model.WorkloadInput;
import com.WorkloadService.workload_service.service.WorkloadService;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/gym", consumes = {"application/JSON"})
@RequiredArgsConstructor
@FeignClient("workload-service")
public class WorkloadController {

    private final WorkloadService workloadService;
    @Autowired
    private EurekaClient eurekaClient;
    private Logger logger = LoggerFactory.getLogger(WorkloadController.class);

    @PutMapping(value = "/putWorkload")
    public String putWorkload(@RequestBody WorkloadInput workload) {
        System.out.println(workload.getUsername() + " " + workload.isActive());
        if (workload == null || workload.getUsername() == null) {
            logger.warn("Insufficient data, missing username");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        workloadService.createMonthlyTraining(workload);
        //doooooo

        return "index";
    }

    @GetMapping(value = "/getWorkload")
    public void getTrainersMonthlySummary(Model model) {

        TrainersMonthlyTrainings report = workloadService.getTrainersMonthlySummary();

        model.addAttribute("TrainersMonthlyTrainings", report);
    }
}
