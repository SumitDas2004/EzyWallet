package com.project.EzyWallet.UserService.dao;

import com.project.EzyWallet.UserService.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    @Cacheable(value = "EzyWallet_UserService", key = "#username.toLowerCase()", unless="#result==null || #result.isLoggedOut()==true")
    @Query("select u from User as u where u.email=:username")
    User findByUsername(String username);

    User findByPhone(String phone);
}
