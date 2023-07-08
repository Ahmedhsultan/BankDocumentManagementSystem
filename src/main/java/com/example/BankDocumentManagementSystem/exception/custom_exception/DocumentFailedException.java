package com.example.BankDocumentManagementSystem.exception.custom_exception;

public class DocumentFailedException extends RuntimeException {
    public DocumentFailedException(String msg){
        super(msg);
    }
    public DocumentFailedException(){
        super();
    }
}
