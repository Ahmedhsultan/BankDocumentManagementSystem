package com.example.BankDocumentManagementSystem.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public class CommentDTO {
    private Integer id;
    private PostDTO post;
    private Set<DocumentDTO> documents;
}
