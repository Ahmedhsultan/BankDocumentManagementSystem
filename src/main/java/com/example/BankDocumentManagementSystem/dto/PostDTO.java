package com.example.BankDocumentManagementSystem.dto;

import java.util.Set;

public class PostDTO {
    private Integer id;
    private Set<DocumentDTO> documents;
    private Set<CommentDTO> comments;
}
