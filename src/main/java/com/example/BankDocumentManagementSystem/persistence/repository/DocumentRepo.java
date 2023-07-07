package com.example.BankDocumentManagementSystem.persistence.repository;

import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Integer> {
}
