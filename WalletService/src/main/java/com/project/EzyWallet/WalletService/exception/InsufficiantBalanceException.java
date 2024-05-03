package com.project.EzyWallet.WalletService.exception;

public class InsufficiantBalanceException extends Exception{
    public InsufficiantBalanceException(){
        super("Insufficient Balance.");
    }
}
