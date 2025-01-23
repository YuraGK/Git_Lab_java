package com.WorkloadService.workload_service.repository;

import com.WorkloadService.workload_service.model.TrainersWorkload;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkloadMongoDBRepository extends MongoRepository<TrainersWorkload, UUID> {
    TrainersWorkload findByUsername(String username);

    void updateByUsername(String username);

}
