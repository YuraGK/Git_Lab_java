package com.epam.gym.atlass_gym.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class CustomMetricService {
    private final MeterRegistry meterRegistry;

    // Constructor injection of MeterRegistry
    public CustomMetricService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementNumOfRegistrations() {
        meterRegistry.counter("number_of_registrations").increment();
    }

    public void updateCustomGauge(double value) {
        meterRegistry.gauge("logged_in_as", value);
    }
}
