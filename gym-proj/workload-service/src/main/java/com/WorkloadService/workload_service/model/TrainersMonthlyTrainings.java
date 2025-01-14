package com.WorkloadService.workload_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainersMonthlyTrainings {

    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private List<String> years;

}
