package com.project.EzyWallet.WalletService.Entity;

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
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    double balance;
    @Column(unique = true)
    String phone;
    @CreationTimestamp
    Date createdAt;
    @UpdateTimestamp
    Date updatedAt;
}
