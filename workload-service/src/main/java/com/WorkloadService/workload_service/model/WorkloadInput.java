package com.WorkloadService.workload_service.model;

import java.io.Serializable;

public class WorkloadInput extends Workload implements Serializable {

    private String type; //(ADD/DELETE)

    public String getType() {
        return type;
    }

}
