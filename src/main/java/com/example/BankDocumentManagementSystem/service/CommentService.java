package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.dto.CommentDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import com.example.BankDocumentManagementSystem.persistence.repository.CommentRepo;
import com.example.BankDocumentManagementSystem.util.mapper.CommentMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends BaseService<Comment, CommentRepo, Integer, CommentDTO, CommentMapper>{
}
