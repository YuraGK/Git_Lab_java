package com.WorkloadService.workload_service.service;


import com.WorkloadService.workload_service.model.TrainersMonthlyTrainings;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Session;
import org.springframework.jms.core.MessageCreator;

public class MessageCreatorService implements MessageCreator {

    private TrainersMonthlyTrainings message;

    public MessageCreatorService(TrainersMonthlyTrainings message) {
        this.message = message;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage(message);
        return objectMessage;
    }
}
