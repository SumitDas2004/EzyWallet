package com.project.EzyWallet.TransactionService.dao;

import com.project.EzyWallet.TransactionService.entity.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t from Transaction t WHERE t.sender=:user  OR t.receiver=:user")
    List<Transaction> findTransactionsInvolvingUser(String user, PageRequest request);
}
