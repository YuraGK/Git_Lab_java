package com.warehouse.tmp.module;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;


public class WorkloadInput extends Workload implements Serializable {

    private String type; //(ADD/DELETE)

    @JsonCreator
    public WorkloadInput(@JsonProperty("username") String username, @JsonProperty("firstName") String firstName,
                         @JsonProperty("lastName") String lastName, @JsonProperty("active") boolean active,
                         @JsonProperty("date") LocalDate trainingDate,
                         @JsonProperty("duration") double trainingDuration, @JsonProperty("type") String type) {
        super(username, firstName, lastName, active, trainingDate, trainingDuration);
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
