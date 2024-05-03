package com.project.EzyWallet.TransactionService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitiateTransactionRequestModel {
    String receiver;
    double amount;
    String purpose;

}
