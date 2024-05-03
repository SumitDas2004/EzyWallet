package com.project.EzyWallet.notification.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    JavaMailSender mailSender;

    public void sendMail(String to, String subject, String body){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subject);
        mail.setTo(to);
        mail.setText(body);
        mailSender.send(mail);
    }
}
