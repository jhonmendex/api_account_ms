package com.misiontic.account_ms.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message){
        super(message);
    }
}
