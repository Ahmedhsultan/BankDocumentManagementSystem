package com.example.BankDocumentManagementSystem.dto.request;

import org.springframework.web.bind.annotation.RequestParam;

public record CommentDTOReq (String body, String title, String documentName, String userName){}
