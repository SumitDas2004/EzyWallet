package com.project.EzyWallet.UserService.entity;

import com.project.EzyWallet.UserService.constants.UserAuthorities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GenericGenerator(name="UidGenerator", strategy = "com.project.EzyWallet.UserService.configuration.CustomUserIdGenerator")
    @GeneratedValue(generator = "UidGenerator", strategy = GenerationType.SEQUENCE)
    private String id;
    @Column(unique = true, nullable = false)
    private String email; //Email is treated as username

    @Column(unique = true, nullable = false)
    @Size(min=9, max=12)
    private String phone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<UserAuthorities> authorities;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    private boolean isLoggedOut;

    @Override
    public List<GrantedAuthority> getAuthorities(){
        return authorities.stream().map(auth-> new SimpleGrantedAuthority(auth.toString())).collect(Collectors.toList());
    }


    public List<UserAuthorities> getRawAuthorities(){
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
