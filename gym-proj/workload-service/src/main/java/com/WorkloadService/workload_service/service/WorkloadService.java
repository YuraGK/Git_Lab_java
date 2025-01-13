package com.WorkloadService.workload_service.service;

import com.WorkloadService.workload_service.model.TrainersMonthlyTrainings;
import com.WorkloadService.workload_service.model.Workload;
import com.WorkloadService.workload_service.model.WorkloadInput;
import com.WorkloadService.workload_service.repository.WorkloadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkloadService {

    @Autowired
    private final WorkloadRepository workloadRepository;
    private Logger logger = LoggerFactory.getLogger(WorkloadService.class);

    public void createMonthlyTraining(WorkloadInput workload) {

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

        List<Workload> workList = (List<Workload>) workloadRepository.findAll();

        if (workList.isEmpty()) return null;
        Workload first = workList.get(0);
        TrainersMonthlyTrainings report = TrainersMonthlyTrainings.builder()
                .username(first.getUsername())
                .firstName(first.getFirstName())
                .lastName(first.getLastName())
                .isActive(first.isActive())
                .years(getSchedule(workList))
                .build();

        return report;
    }

    private List<List<String>> getSchedule(List<Workload> workList) {
        List<List<String>> result = new ArrayList<List<String>>();

        List<String> temp = new ArrayList<String>();
        int year = workList.get(0).getDate().getYear();


        for (Workload w : workList) {
            if (w.getDate().getYear() != year) {
                result.add(temp);
                year = w.getDate().getYear();
                temp = new ArrayList<String>();
            }
            temp.add(w.getDate() + " " + w.getDuration());

        }


        return result;
    }
}
