package com.project.EzyWallet.UserService.dto;

import com.project.EzyWallet.UserService.constants.UserAuthorities;
import com.project.EzyWallet.UserService.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDto {

    private String email;
    private String phone;
    private String name;
    private String password;
    private List<UserAuthorities> authorities;

    public User toUser(){
        return User.builder()
                .email(this.email)
                .phone(this.phone)
                .name(this.name)
                .password(this.password)
                .authorities(this.authorities)
                .build();
    }
}
