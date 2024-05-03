package com.project.EzyWallet.WalletService.exception;

public class SenderWalletDoesNotExistException extends Exception{
    public SenderWalletDoesNotExistException(){
        super("Wallet does not exist. Please create wallet.");
    }
}