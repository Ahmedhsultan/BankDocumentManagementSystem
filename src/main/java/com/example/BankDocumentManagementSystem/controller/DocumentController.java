package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.dto.CommentDTO;
import com.example.BankDocumentManagementSystem.dto.DocumentDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.repository.CommentRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.service.CommentService;
import com.example.BankDocumentManagementSystem.service.DocumentService;
import com.example.BankDocumentManagementSystem.util.mapper.CommentMapper;
import com.example.BankDocumentManagementSystem.util.mapper.DocumentMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("document")
public class DocumentController extends BaseController<Integer, Document, DocumentRepo, DocumentDTO, DocumentMapper, DocumentService>{
}
