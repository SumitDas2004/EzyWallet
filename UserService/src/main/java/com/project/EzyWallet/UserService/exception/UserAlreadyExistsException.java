package com.project.EzyWallet.UserService.exception;

public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(){
        super("An user with given email or phone number already exists.");
    }
}
