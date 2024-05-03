package com.project.EzyWallet.TransactionService.dao;

import com.project.EzyWallet.TransactionService.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {

}
