package com.WorkloadService.workload_service.repository;

import com.WorkloadService.workload_service.model.TrainersWorkload;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkloadMongoDBRepository extends MongoRepository<TrainersWorkload, Integer> {
    TrainersWorkload findByUsername(String username);

}
