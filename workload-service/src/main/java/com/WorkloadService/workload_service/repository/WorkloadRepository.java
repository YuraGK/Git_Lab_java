package com.WorkloadService.workload_service.repository;

import com.WorkloadService.workload_service.model.Workload;
import org.springframework.data.repository.CrudRepository;

public interface WorkloadRepository extends CrudRepository<Workload, String> {
}
