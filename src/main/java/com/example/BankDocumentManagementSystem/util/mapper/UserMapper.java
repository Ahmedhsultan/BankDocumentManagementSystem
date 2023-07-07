package com.example.BankDocumentManagementSystem.util.mapper;

import com.example.BankDocumentManagementSystem.dto.CommentDTO;
import com.example.BankDocumentManagementSystem.dto.DocumentDTO;
import com.example.BankDocumentManagementSystem.dto.PostDTO;
import com.example.BankDocumentManagementSystem.dto.UserDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper extends BaseMapper<User, UserDTO> {
    @Autowired
    @Lazy
    protected CommentMapper commentMapper;
    @Autowired
    @Lazy
    protected DocumentMapper documentMapper;
    @Autowired
    @Lazy
    protected PostMapper postMapper;

    @Override
    public UserDTO toDTO(User user) {
        Set<DocumentDTO> documentDTOs = user.getDocuments().stream()
                .map(documentMapper::toDTO)
                .collect(Collectors.toSet());

        Set<PostDTO> postDTOS = user.getPosts().stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toSet());

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .documents(documentDTOs)
                .posts(postDTOS)
                .build();
    }
}
