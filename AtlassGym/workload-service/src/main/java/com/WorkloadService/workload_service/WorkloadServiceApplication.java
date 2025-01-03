package com.WorkloadService.workload_service;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
public class WorkloadServiceApplication {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;
	

    public static void main(String[] args) {
        SpringApplication.run(WorkloadServiceApplication.class, args);
    }

}
