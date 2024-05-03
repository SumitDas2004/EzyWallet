package com.project.EzyWallet.TransactionService.service;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient("USER")
public interface UserServiceFeign {
    @GetMapping("/getEmailFromPhone/{phone}")
    String getEmailFromPhone(@PathVariable String phone);

    @GetMapping("/getUser/{username}")
    Map<String, Object> getUserFromUsername(@PathVariable String username);
}
