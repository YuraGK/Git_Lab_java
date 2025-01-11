package com.epam.gym.atlass_gym.service;

import com.epam.gym.atlass_gym.model.TrainersMonthlyTrainings;
import com.epam.gym.atlass_gym.model.Workload;
import com.epam.gym.atlass_gym.model.WorkloadInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "WORKLOAD-SERVICE")
public interface WorkloadCallService {

    @PutMapping(value = "/putWorkload")
    public Workload putWorkload(WorkloadInput workload);

    @PostMapping(value = "/getWorkload")
    public TrainersMonthlyTrainings getReport();
}
