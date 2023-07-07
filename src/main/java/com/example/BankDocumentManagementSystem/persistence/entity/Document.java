package com.example.BankDocumentManagementSystem.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    @Column(name = "file_name")
    private String fileName;
    @ManyToOne
    private User user;
    @ManyToMany
    private Set<Post> posts;
    @ManyToMany
    private Set<Comment> comments;
}
