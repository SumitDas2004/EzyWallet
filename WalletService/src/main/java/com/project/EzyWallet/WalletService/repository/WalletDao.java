package com.project.EzyWallet.WalletService.repository;

import com.project.EzyWallet.WalletService.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WalletDao extends JpaRepository<Wallet, Integer> {
    Wallet findByPhone(String phone);

    @Transactional
    @Modifying
    @Query("UPDATE Wallet w SET w.balance=w.balance+:amount WHERE w.phone = :user")
    void recharge(String user, int amount);
}
