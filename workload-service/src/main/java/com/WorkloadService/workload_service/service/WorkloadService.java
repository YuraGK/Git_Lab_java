package com.WorkloadService.workload_service.service;

import com.WorkloadService.workload_service.model.TrainersWorkload;
import com.WorkloadService.workload_service.repository.WorkloadMongoDBRepository;
import com.WorkloadService.workload_service.repository.WorkloadRepository;
import com.warehouse.tmp.module.TrainersMonthlyTrainings;
import com.warehouse.tmp.module.Workload;
import com.warehouse.tmp.module.WorkloadInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkloadService {

    private final WorkloadMongoDBRepository workloadMongoDBRepository;
    @Autowired
    private final WorkloadRepository workloadRepository;
    private Logger logger = LoggerFactory.getLogger(WorkloadService.class);

    public Workload createMonthlyTraining(WorkloadInput workload) {

        //use MongoDB
        Workload training = new Workload(workload.getUsername(), workload.getFirstName(),
                workload.getLastName(), workload.isActive(), workload.getDate(), workload.getDuration());
        
        workloadMongoDBRepository.save(new TrainersWorkload(workload.getUsername(), workload.getFirstName(),
                workload.getLastName(), workload.isActive(),
                getSchedule(Arrays.asList(training))));


        workloadRepository.save(training);
        logger.info("Training appointment saved");
        return training;
    }


    public TrainersMonthlyTrainings getTrainersMonthlySummary(String username) {

        List<Workload> workList = (List<Workload>) workloadRepository.findAll();
        logger.info("workList: " + workList + " " + workList.size());

        try {
            TrainersWorkload result = workloadMongoDBRepository.findByUsername(username);
            logger.info("workList: " + result);
            /*Workload first = workList.get(0);
            TrainersMonthlyTrainings report = new TrainersMonthlyTrainings(
                    first.getUsername(), first.getFirstName(),
                    first.getLastName(), first.isActive(),
                    getSchedule(workList));*/
            return result;
        } catch (Exception e) {

        }


        return new TrainersMonthlyTrainings();
    }

    private List<String> getSchedule(List<Workload> workList) {

        List<String> temp = new ArrayList<String>();

        for (Workload w : workList) {
            temp.add(w.getDate() + " " + w.getDuration());
        }


        return temp;
    }
}
