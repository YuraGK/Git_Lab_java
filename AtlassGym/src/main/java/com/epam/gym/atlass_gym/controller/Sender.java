package com.epam.gym.atlass_gym.controller;

import com.warehouse.tmp.module.TrainersMonthlyTrainings;
import com.warehouse.tmp.module.Workload;
import com.warehouse.tmp.module.WorkloadInput;
import jakarta.jms.Destination;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
public class Sender {
    private final JmsTemplate jmsTemplate;

    private Logger logger = LoggerFactory.getLogger(Sender.class);

    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Workload sendMessage(final WorkloadInput message) {
        /*jmsTemplate.convertAndSend("putworkload", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage(message);
                return objectMessage;
            }
        });*/
        String responseQueue = "getworkloadresponse";
        Destination replyTo = new ActiveMQQueue(responseQueue);
        jmsTemplate.convertAndSend("putworkload", message);
        return (Workload) jmsTemplate.receiveAndConvert(responseQueue);

    }

    public TrainersMonthlyTrainings sendMessage(final String message) {
        /*jmsTemplate.convertAndSend("getworkload", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage(message);
                return objectMessage;
            }
        });*/

        String responseQueue = "getreport";
        Destination replyTo = new ActiveMQQueue(responseQueue);
        jmsTemplate.convertAndSend("getworkload", message);

        return (TrainersMonthlyTrainings) jmsTemplate.receiveAndConvert(responseQueue);

    }

}
