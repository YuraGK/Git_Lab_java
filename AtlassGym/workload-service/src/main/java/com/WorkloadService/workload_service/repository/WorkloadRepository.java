package com.WorkloadService.workload_service.repository;

import com.WorkloadService.workload_service.model.TrainersMonthlyTrainings;
import com.WorkloadService.workload_service.model.Workload;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkloadRepository extends MongoRepository<Workload, String> {
}
