package com.project.EzyWallet.WalletService.exception;

public class ReceiverWalletDoesNotExistException extends Exception{
    public ReceiverWalletDoesNotExistException(){
        super("Receiver wallet does not exist.");
    }
}
