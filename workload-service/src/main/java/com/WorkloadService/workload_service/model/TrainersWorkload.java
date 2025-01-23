package com.WorkloadService.workload_service.model;

import com.warehouse.tmp.module.TrainersMonthlyTrainings;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document
public class TrainersWorkload extends TrainersMonthlyTrainings {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Indexed(unique = true)
    private String username;

    public TrainersWorkload(String username, String firstName,
                            String lastName, boolean isActive, List<String> years) {
        super(username, firstName,
                lastName, isActive, years);
        this.username = username;
    }
}
