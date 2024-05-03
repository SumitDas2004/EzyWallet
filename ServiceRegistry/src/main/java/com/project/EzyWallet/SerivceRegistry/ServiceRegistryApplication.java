package com.project.EzyWallet.SerivceRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;


@SpringBootApplication
@EnableEurekaServer
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class ServiceRegistryApplication {
    public static  void main(String[] args){
        SpringApplication.run(ServiceRegistryApplication.class, args);
    }
}
