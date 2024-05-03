package com.project.EzyWallet.TransactionService.service;

import com.project.EzyWallet.TransactionService.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserServiceFeign userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> userMap = userService.getUserFromUsername(username);
        return User.builder()
                .email((String)userMap.get("username"))
                .authorities((List<String>)userMap.get("authorities"))
                .isLoggedOut((boolean)userMap.get("isLoggedOut"))
                .phone((String)userMap.get("phone"))
                .build();
    }
}
