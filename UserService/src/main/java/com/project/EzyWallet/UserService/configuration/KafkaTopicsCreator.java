package com.project.EzyWallet.UserService.configuration;

import com.project.EzyWallet.UserService.constants.KafkaTopicNames;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsCreator {
    @Bean
    NewTopic userWalletTopic(){
        return TopicBuilder.name(KafkaTopicNames.USER_WALLET_TOPIC).build();
    }

    @Bean
    NewTopic mailSenderTopic(){
        return TopicBuilder.name(KafkaTopicNames.MAIL_SENDER_TOPIC).build();
    }
}
