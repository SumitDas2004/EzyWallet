package com.project.EzyWallet.TransactionService.entity;


import com.project.EzyWallet.TransactionService.constants.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String sender;//sender's phone number
    String receiver;//receiver's phone number
    double amount;
    @Enumerated(EnumType.STRING)
    TransactionStatus status;
    @CreationTimestamp
    Date createdOn;
    @UpdateTimestamp
    Date updatedOn;
    String purpose;


}
