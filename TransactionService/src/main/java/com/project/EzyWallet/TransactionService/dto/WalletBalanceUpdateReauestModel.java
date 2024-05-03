package com.project.EzyWallet.TransactionService.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletBalanceUpdateReauestModel {
    String sender;
    String receiver;
    double amount;
}
