package com.WorkloadService.workload_service.service;

import com.WorkloadService.workload_service.model.TrainersWorkload;
import com.WorkloadService.workload_service.repository.WorkloadMongoDBRepository;
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
    @Autowired
    private WorkloadMongoDBRepository workloadMongoDBRepository;
    private Logger logger = LoggerFactory.getLogger(WorkloadService.class);

    public Workload createMonthlyTraining(WorkloadInput workload) {


        try {
            TrainersWorkload work = workloadMongoDBRepository.findByUsername(workload.getUsername());
            List<String> y = work.getYears();
            y.add(workload.getDate() + " " + workload.getDuration());
            work.setYears(y);
            workloadMongoDBRepository.deleteById(work.getId());
            workloadMongoDBRepository.save(work);
            return new Workload(workload.getUsername(), workload.getFirstName(),
                    workload.getLastName(), workload.isActive(), workload.getDate(), workload.getDuration());
        } catch (Exception e) {
        }


        //use MongoDB
        Workload training = new Workload(workload.getUsername(), workload.getFirstName(),
                workload.getLastName(), workload.isActive(), workload.getDate(), workload.getDuration());

        workloadMongoDBRepository.findAll().size();

        workloadMongoDBRepository.save(new TrainersWorkload(workload.getUsername(), workload.getFirstName(),
                workload.getLastName(), workload.isActive(),
                getSchedule(Arrays.asList(training))));

        logger.info("Training appointment saved");
        return training;
    }


    public TrainersMonthlyTrainings getTrainersMonthlySummary(String username) {

        try {
            TrainersWorkload work = workloadMongoDBRepository.findByUsername(username);
            logger.info("workList: " + work);

            TrainersMonthlyTrainings report = new TrainersMonthlyTrainings(
                    work.getUsername(), work.getFirstName(),
                    work.getLastName(), work.isActive(),
                    work.getYears());
            return report;
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
