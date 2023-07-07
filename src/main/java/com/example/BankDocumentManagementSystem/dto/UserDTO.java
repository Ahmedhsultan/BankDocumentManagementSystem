package com.example.BankDocumentManagementSystem.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public class UserDTO {
    private Integer id;
    private String name;
    private Integer age;
    private Set<DocumentDTO> documents;
    private Set<PostDTO> posts;
}
