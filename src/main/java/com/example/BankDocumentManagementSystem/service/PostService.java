package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.dto.PostDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.Post;
import com.example.BankDocumentManagementSystem.persistence.repository.PostRepo;
import com.example.BankDocumentManagementSystem.util.mapper.PostMapper;
import org.springframework.stereotype.Service;

@Service
public class PostService extends BaseService<Post, PostRepo, Integer, PostDTO, PostMapper> {
}
