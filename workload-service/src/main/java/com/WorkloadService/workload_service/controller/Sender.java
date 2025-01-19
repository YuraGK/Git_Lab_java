package com.WorkloadService.workload_service.controller;

import org.springframework.stereotype.Component;

@Component
public class Sender {/*
    @Autowired
    private JmsTemplate jmsTemplate;

    private Logger logger = LoggerFactory.getLogger(Sender.class);

    public void sendMessage(final TrainersMonthlyTrainings message) {
        jmsTemplate.convertAndSend("getreport", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage(message);
                return objectMessage;
            }
        });
    }
*/
}
