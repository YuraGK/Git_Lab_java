package com.epam.gym.atlass_gym.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainersMonthlyTrainings {

    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private List<List<String>> years;

}
