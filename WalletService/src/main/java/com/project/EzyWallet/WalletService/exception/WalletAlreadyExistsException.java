package com.project.EzyWallet.WalletService.exception;

public class WalletAlreadyExistsException extends Exception{
    public WalletAlreadyExistsException(){
        super("The user already has an active wallet.");
    }
}
