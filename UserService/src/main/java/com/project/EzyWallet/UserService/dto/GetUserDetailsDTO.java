package com.project.EzyWallet.UserService.dto;

import com.project.EzyWallet.UserService.constants.UserAuthorities;
import com.project.EzyWallet.UserService.entity.User;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetUserDetailsDTO {
    private String id;
    private String email; //Email is treated as username
    private String phone;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    public static GetUserDetailsDTO to(User user){
        return GetUserDetailsDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
