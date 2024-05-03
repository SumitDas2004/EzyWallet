package com.project.EzyWallet.notification.Service;

import com.project.EzyWallet.notification.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {
    @Autowired
    MailSenderService mailSender;

    @KafkaListener(topics = {Constants.MAIL_SENDER_TOPIC}, groupId = "mail_service_group")
    void mailListener(Map<String, Object> map){
        String to = (String)map.get("to");
        String subject = (String)map.get("subject");
        String body = (String)map.get("body");
        System.out.println(to+" "+subject+" "+body);
        mailSender.sendMail(to,subject, body);
    }
}
