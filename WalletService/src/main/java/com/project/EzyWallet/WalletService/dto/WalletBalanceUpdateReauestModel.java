package com.project.EzyWallet.WalletService.dto;

import lombok.Data;

@Data
public class WalletBalanceUpdateReauestModel {
    String sender;
    String receiver;
    double amount;
}
