package com.WorkloadService.workload_service.service;

import com.WorkloadService.workload_service.model.TrainersMonthlyTrainings;
import com.WorkloadService.workload_service.model.Workload;
import com.WorkloadService.workload_service.model.WorkloadInput;
import com.WorkloadService.workload_service.repository.WorkloadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkloadService {

    private Logger logger = LoggerFactory.getLogger(WorkloadService.class);

    private final WorkloadRepository workloadRepository;

    public void createMonthlyTraining(WorkloadInput workload){

        Workload training = Workload.builder()
                .username(workload.getUsername())
                .firstName(workload.getFirstName())
                .lastName(workload.getLastName())
                .isActive(workload.isActive())
                .date(workload.getDate())
                .duration(workload.getDuration())
                .build();

        workloadRepository.save(training);
        logger.info("Training appointment saved");
    }


    public TrainersMonthlyTrainings getTrainersMonthlySummary() {

        List<Workload> workList = workloadRepository.findAll();

        return null;
    }
}
