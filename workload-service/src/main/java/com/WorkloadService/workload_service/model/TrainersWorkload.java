package com.WorkloadService.workload_service.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document
@Builder
@Data
@AllArgsConstructor
public class TrainersWorkload implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @Indexed(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private List<String> years;

    public TrainersWorkload() {
        years = new ArrayList<String>();
    }

    public TrainersWorkload(String username, String firstName,
                            String lastName, boolean isActive, List<String> years) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.years = years;
    }
}
