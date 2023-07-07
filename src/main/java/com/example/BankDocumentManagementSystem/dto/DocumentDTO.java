package com.example.BankDocumentManagementSystem.dto;

import java.util.Set;

public class DocumentDTO {
    private Integer id;
    private String url;
    private String fileName;
    private UserDTO user;
    private Set<PostDTO> posts;
    private Set<CommentDTO> comments;
}
