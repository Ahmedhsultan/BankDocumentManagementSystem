package com.example.BankDocumentManagementSystem.util.mapper;

import com.example.BankDocumentManagementSystem.dto.CommentDTO;
import com.example.BankDocumentManagementSystem.dto.DocumentDTO;
import com.example.BankDocumentManagementSystem.dto.PostDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommentMapper extends BaseMapper<Comment, CommentDTO>{
    @Autowired
    @Lazy
    protected DocumentMapper documentMapper;
    @Autowired
    @Lazy
    protected PostMapper postMapper;
    @Autowired
    @Lazy
    protected UserMapper userMapper;

    @Override
    public CommentDTO toDTO(Comment comment) {

        Set<DocumentDTO> documentDTOs = comment.getDocuments().stream()
                .map(documentMapper::toDTO)
                .collect(Collectors.toSet());

        PostDTO postDTO = postMapper.toDTO(comment.getPost());

        return CommentDTO.builder()
                .id(comment.getId())
                .documents(documentDTOs)
                .post(postDTO)
                .build();
    }
}
