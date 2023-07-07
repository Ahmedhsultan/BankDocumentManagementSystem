package com.example.BankDocumentManagementSystem.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Document> documents;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Comment> comments;
}
