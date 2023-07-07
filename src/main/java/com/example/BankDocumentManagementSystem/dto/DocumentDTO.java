package com.example.BankDocumentManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
@Data
@Builder
public class DocumentDTO {
    private Integer id;
    private String url;
    private String fileName;
    private UserDTO user;
    private Set<PostDTO> posts;
    private Set<CommentDTO> comments;
}
