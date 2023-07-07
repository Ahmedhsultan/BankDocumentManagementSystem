package com.example.BankDocumentManagementSystem.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public class PostDTO {
    private Integer id;
    private Set<DocumentDTO> documents;
    private Set<CommentDTO> comments;
}
