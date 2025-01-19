package com.WorkloadService.workload_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@EnableAutoConfiguration
@SpringBootApplication
public class WorkloadServiceApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(WorkloadServiceApplication.class, args);

    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WorkloadServiceApplication.class);
    }
}

