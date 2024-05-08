package com.project.EzyWallet.UserService.service;

import com.project.EzyWallet.UserService.constants.KafkaTopicNames;
import com.project.EzyWallet.UserService.dao.UserDao;
import com.project.EzyWallet.UserService.dto.LoginDto;
import com.project.EzyWallet.UserService.dto.RegistrationDto;
import com.project.EzyWallet.UserService.entity.User;
import com.project.EzyWallet.UserService.exception.InvalidCredentialsFormatException;
import com.project.EzyWallet.UserService.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    KafkaSenderService kafkaSenderService;

    @Autowired
    AuthenticationManager authenticationManager;

    public String register(RegistrationDto request) throws UserAlreadyExistsException, InvalidCredentialsFormatException {
        if(existingUser(request.getEmail(), request.getPhone()))
            throw new UserAlreadyExistsException();
        if(!isValidName(request.getName()))throw new InvalidCredentialsFormatException("Name must have atleast 3 characters");
        if(!isValidEmail(request.getEmail()))throw new InvalidCredentialsFormatException("Invalid email format.");
        if(!isValidPassword(request.getPassword()))throw new InvalidCredentialsFormatException("Invalid password format. Password must be at least 8 characters long and should contain at least one uppercase character, one lowercase character, one number and one special character");
        if(!isValidPhone(request.getPhone()))throw new InvalidCredentialsFormatException("Invalid phone number.");

        User user = request.toUser();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setLoggedOut(false);
        user = userDao.save(user);
        //Creation map for publishing wallet creation request message in kafka
        kafkaSenderService.send(KafkaTopicNames.USER_WALLET_TOPIC, user.getPhone());
        return jwtService.generateToken(user.getUsername());
    }

    private boolean existingUser(String email, String phone) {
        User user= userDao.findByUsername(email);
        if(user!=null)return true;
        user = userDao.findByPhone(phone);
        return user!=null;
    }

    public String login(LoginDto request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword(), new ArrayList<>()));
        User user = userDao.findByUsername(request.getEmail());
        if(user==null)throw new BadCredentialsException("");
        user.setLoggedOut(false);
        userDao.save(user);
        return jwtService.generateToken(user.getUsername());
    }

    @CacheEvict(value="EzyWallet_UserService", key="#username")
    public void logout(String username) {
        User user = userDao.findByUsername(username);
        user.setLoggedOut(true);
        userDao.save(user);
    }

    public String getEmailFromPhone(String phone) {
        return userDao.findByPhone(phone).getEmail();
    }

    public User getUserFromUsername(String username) {
        return userDao.findByUsername(username);
    }

    private boolean isValidEmail(String email) {
        email = email.trim();
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regexPattern);
    }

    private boolean isValidPassword(String password) {
        String regexPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regexPattern);
    }

    private boolean isValidPhone(String phone) {
        phone = phone.trim();
        if(phone.length()<4 || phone.length()>12)return false;
        for(char c:phone.toCharArray())if(!Character.isDigit(c))return false;
        return true;
    }

    private  boolean isValidName(String name){
        if(name==null)return false;
        return name.trim().length()>=3;
    }
}
