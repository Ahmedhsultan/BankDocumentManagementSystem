package com.example.BankDocumentManagementSystem.exception.custom_exception;

public class DocumentFaildException extends RuntimeException {
    public DocumentFaildException(String msg){
        super(msg);
    }
    public DocumentFaildException(){
        super();
    }
}
