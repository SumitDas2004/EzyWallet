package com.project.EzyWallet.UserService.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public ObjectMapper om(){return new ObjectMapper();}
}
