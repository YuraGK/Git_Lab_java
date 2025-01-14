package com.epam.gym.atlass_gym.service;

import com.epam.gym.atlass_gym.model.TrainersMonthlyTrainings;
import com.epam.gym.atlass_gym.model.Workload;
import com.epam.gym.atlass_gym.model.WorkloadInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "WORKLOAD-SERVICE")
public interface WorkloadCallService {

    @PutMapping(value = "/putWorkload")
    public Workload putWorkload(WorkloadInput workload);

    @GetMapping(value = "/getWorkload", consumes = {"application/JSON"}, produces = {"application/JSON"})
    public TrainersMonthlyTrainings getReport();
}
