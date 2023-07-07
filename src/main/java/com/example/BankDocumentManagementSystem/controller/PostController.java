package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.dto.PostDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.Post;
import com.example.BankDocumentManagementSystem.persistence.repository.PostRepo;
import com.example.BankDocumentManagementSystem.service.PostService;
import com.example.BankDocumentManagementSystem.util.mapper.PostMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("post")
public class PostController extends BaseController<Integer, Post, PostRepo, PostDTO, PostMapper, PostService>{
}
