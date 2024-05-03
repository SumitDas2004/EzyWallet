package com.project.EzyWallet.WalletService.repository;

import com.project.EzyWallet.WalletService.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletDao extends JpaRepository<Wallet, Integer> {
    Wallet findByPhone(String phone);
}
