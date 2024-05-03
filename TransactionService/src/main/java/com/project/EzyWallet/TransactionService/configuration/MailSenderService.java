package com.project.EzyWallet.TransactionService.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate ;
    public void sendMail(String topic, Object message){
        kafkaTemplate.send(topic, message);
    }
}
