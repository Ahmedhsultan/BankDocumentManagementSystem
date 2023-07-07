package com.example.BankDocumentManagementSystem.persistence.repository;

import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
