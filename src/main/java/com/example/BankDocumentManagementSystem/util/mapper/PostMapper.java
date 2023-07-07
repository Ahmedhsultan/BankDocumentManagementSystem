package com.example.BankDocumentManagementSystem.util.mapper;

import com.example.BankDocumentManagementSystem.dto.CommentDTO;
import com.example.BankDocumentManagementSystem.dto.DocumentDTO;
import com.example.BankDocumentManagementSystem.dto.PostDTO;
import com.example.BankDocumentManagementSystem.dto.UserDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.Post;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PostMapper extends BaseMapper <Post, PostDTO> {
    @Override
    public PostDTO toDTO(Post post) {
        Set<CommentDTO> commentDTOS = post.getComments().stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toSet());

        Set<DocumentDTO> documentDTOs = post.getDocuments().stream()
                .map(documentMapper::toDTO)
                .collect(Collectors.toSet());

        return PostDTO.builder()
                .id(post.getId())
                .comments(commentDTOS)
                .documents(documentDTOs)
                .build();
    }
}
