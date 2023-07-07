package com.example.BankDocumentManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class CommentDTO {
    private Integer id;
    private PostDTO post;
    private Set<DocumentDTO> documents;
}
