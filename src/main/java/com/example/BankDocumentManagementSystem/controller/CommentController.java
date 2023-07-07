package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.dto.CommentDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import com.example.BankDocumentManagementSystem.persistence.repository.CommentRepo;
import com.example.BankDocumentManagementSystem.service.CommentService;
import com.example.BankDocumentManagementSystem.util.mapper.CommentMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class CommentController extends BaseController<Integer, Comment, CommentRepo, CommentDTO, CommentMapper, CommentService>{
}
