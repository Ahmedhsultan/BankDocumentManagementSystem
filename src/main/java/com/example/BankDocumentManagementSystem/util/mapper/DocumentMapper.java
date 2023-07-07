package com.example.BankDocumentManagementSystem.util.mapper;

import com.example.BankDocumentManagementSystem.dto.CommentDTO;
import com.example.BankDocumentManagementSystem.dto.DocumentDTO;
import com.example.BankDocumentManagementSystem.dto.PostDTO;
import com.example.BankDocumentManagementSystem.dto.UserDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import org.hibernate.annotations.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DocumentMapper extends BaseMapper<Document, DocumentDTO> {
    @Autowired
    @Lazy
    protected CommentMapper commentMapper;
    @Autowired
    @Lazy
    protected PostMapper postMapper;
    @Autowired
    @Lazy
    protected UserMapper userMapper;

    @Override
    public DocumentDTO toDTO(Document document) {
        UserDTO userDTO = userMapper.toDTO(document.getUser());

        Set<CommentDTO> commentDTOS = document.getComments().stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toSet());

        Set<PostDTO> postDTOS = document.getPosts().stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toSet());

        return DocumentDTO.builder()
                .id(document.getId())
                .url(document.getUrl())
                .fileName(document.getFileName())
                .user(userDTO)
                .comments(commentDTOS)
                .posts(postDTOS)
                .build();
    }
}
