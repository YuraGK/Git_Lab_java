package com.WorkloadService.workload_service.controller;


import com.WorkloadService.workload_service.service.WorkloadService;
import com.warehouse.tmp.module.TrainersMonthlyTrainings;
import com.warehouse.tmp.module.Workload;
import com.warehouse.tmp.module.WorkloadInput;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class Receiver {

    private final JmsTemplate jmsTemplate;

    @Autowired
    MessageConverter messageConverter;
    private final WorkloadService workloadService;

    private Logger logger = LoggerFactory.getLogger(WorkloadController.class);

    public void saveWorkload(final Message message) throws JMSException {
        WorkloadInput workload = (WorkloadInput) messageConverter.fromMessage(message);
        System.out.println(workload.getUsername() + " " + workload.isActive());
        if (workload == null || workload.getUsername() == null) {
            logger.warn("Insufficient data, missing username");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Workload result = workloadService.createMonthlyTraining(workload);
        logger.info("result: " + result);
        jmsTemplate.convertAndSend("getworkloadresponse", result);
        logger.info("putreport sent");
    }

    public void sendReport(final Message message) throws JMSException {
        String username = (String) messageConverter.fromMessage(message);

        logger.info("workload1");
        TrainersMonthlyTrainings report = workloadService.getTrainersMonthlySummary(username);
        logger.info("report: " + report);

        jmsTemplate.convertAndSend("getreport", report);
        logger.info("getreport sent");
    }

}
