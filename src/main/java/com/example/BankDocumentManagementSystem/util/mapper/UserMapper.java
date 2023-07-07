package com.example.BankDocumentManagementSystem.util.mapper;

import com.example.BankDocumentManagementSystem.dto.responce.DocumentDTOResp;
import com.example.BankDocumentManagementSystem.dto.responce.PostDTOResp;
import com.example.BankDocumentManagementSystem.dto.responce.UserDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper extends BaseMapper<User, UserDTOResp> {
    @Override
    public UserDTOResp toDTO(User user) {
        Set<DocumentDTOResp> documentDTOResps = user.getDocuments().stream()
                .map(documentMapper::toDTO)
                .collect(Collectors.toSet());

        Set<PostDTOResp> postDTOResps = user.getPosts().stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toSet());

        return UserDTOResp.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .name(user.getName())
                .age(user.getAge())
                .documents(documentDTOResps)
                .posts(postDTOResps)
                .build();
    }
}
