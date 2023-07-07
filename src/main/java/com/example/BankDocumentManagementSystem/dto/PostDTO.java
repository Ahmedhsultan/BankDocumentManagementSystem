package com.example.BankDocumentManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PostDTO {
    private Integer id;
    private Set<DocumentDTO> documents;
    private Set<CommentDTO> comments;
}
