package com.project.EzyWallet.UserService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSenderService {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    public void send(String topic, Object message){
        kafkaTemplate.send(topic, message);
    }
}
