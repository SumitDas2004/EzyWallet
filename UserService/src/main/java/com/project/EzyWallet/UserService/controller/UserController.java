package com.project.EzyWallet.UserService.controller;

import com.project.EzyWallet.UserService.dto.GetUserDetailsDTO;
import com.project.EzyWallet.UserService.dto.LoginDto;
import com.project.EzyWallet.UserService.dto.RegistrationDto;
import com.project.EzyWallet.UserService.entity.User;
import com.project.EzyWallet.UserService.exception.UserAlreadyExistsException;
import com.project.EzyWallet.UserService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/userDetails")
    public ResponseEntity<?> getUserDetails(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserFromUsername(username);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("data", GetUserDetailsDTO.to(user));
        map.put("message", "Success");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto request){
        try {
            String token = userService.register(request);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("token", token);
            map.put("message", "User registration successful.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(UserAlreadyExistsException e){
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request){
            String token = userService.login(request);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("token", token);
            map.put("message", "User login successful.");
            return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/getauth")
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.logout(username);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "User logout successful.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //Method to be used by Api Gateway for Authentication purpose
    @GetMapping("/getUser/{username}")
    public Map<String, Object> getUserFromUsername(@PathVariable String username){
        User user =userService.getUserFromUsername(username);
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("authorities", user.getRawAuthorities());
        map.put("isLoggedOut", user.isLoggedOut());
        map.put("phone", user.getPhone());
        return map;
    }



    //Exception handling methods
    Logger logger =  LoggerFactory.getLogger(UserController.class);

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptionHandler(){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Incorrect username or password.");
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<?> internalAuthenticationServiceExceptionHandler(){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Incorrect username or password.");
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerErrorExceptionHandler(Exception e){
        logger.info(e.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Internal Server Error.");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // These methods are for internal use by other microservices
    @GetMapping("/getEmailFromPhone/{phone}")
    public String getEmailFromPhone(@PathVariable String phone){
        return userService.getEmailFromPhone(phone);
    }
}
