package com.WorkloadService.workload_service.controller;


import com.netflix.discovery.EurekaClient;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/*
@RestController
@RequestMapping(consumes = {"application/JSON"})*/

@Component
@RequiredArgsConstructor
public class WorkloadController {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Autowired
    private Receiver receiver;
    
    @JmsListener(destination = "putworkload",
            containerFactory = "jmsTopicContainerFactory")
    public void putWorkload(Message message) throws JMSException {
        receiver.saveWorkload(message);
    }

    @JmsListener(destination = "getworkload",
            containerFactory = "jmsTopicContainerFactory")
    public void getTrainersMonthlySummary(Message message) throws JMSException {
        receiver.sendReport();
    }

}
