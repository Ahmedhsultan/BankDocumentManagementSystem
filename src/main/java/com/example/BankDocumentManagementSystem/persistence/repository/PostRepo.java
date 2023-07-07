package com.example.BankDocumentManagementSystem.persistence.repository;

import com.example.BankDocumentManagementSystem.persistence.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {
}
