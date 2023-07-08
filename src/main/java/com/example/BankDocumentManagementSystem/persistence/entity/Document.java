package com.example.BankDocumentManagementSystem.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    @Column(name = "file_hash")
    private String fileHash;
    @Column(name = "original_file_name")
    private String originalFileName;
    @ManyToOne
    private User user;
    @ManyToMany
    private Set<Post> posts;
    @ManyToMany
    private Set<Comment> comments;
}
