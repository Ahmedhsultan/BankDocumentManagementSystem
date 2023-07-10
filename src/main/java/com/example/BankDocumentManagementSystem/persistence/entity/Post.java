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
@Entity
@Table(name = "post")
public class Post {
    @Id
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER)
    private Document document;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "post")
    private Set<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
