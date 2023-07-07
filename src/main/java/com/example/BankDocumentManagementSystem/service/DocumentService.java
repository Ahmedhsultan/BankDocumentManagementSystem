package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.dto.DocumentDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.util.mapper.DocumentMapper;
import org.springframework.stereotype.Service;

@Service
public class DocumentService extends BaseService<Document, DocumentRepo, Integer, DocumentDTO, DocumentMapper> {
}
