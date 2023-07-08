package com.example.BankDocumentManagementSystem.util.constant;

import org.springframework.stereotype.Component;

@Component
public class Constant {
    public static final String SYS_DIR = System.getProperty("user.dir");
    public static final String FOLDER_PATH = "/upload/document";
    public static final String UPLOAD_FILE_PATH = SYS_DIR + FOLDER_PATH;
}
