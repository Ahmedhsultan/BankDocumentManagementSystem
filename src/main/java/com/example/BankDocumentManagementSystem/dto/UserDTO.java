package com.example.BankDocumentManagementSystem.dto;

import java.util.Set;

public class UserDTO {
    private Integer id;
    private String name;
    private Integer age;
    private Set<DocumentDTO> documents;
    private Set<PostDTO> posts;
}
