package com.example.BankDocumentManagementSystem.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "comment")
public class Comment {
    @Id
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @ManyToOne(fetch = FetchType.EAGER)
    private Document document;
}
