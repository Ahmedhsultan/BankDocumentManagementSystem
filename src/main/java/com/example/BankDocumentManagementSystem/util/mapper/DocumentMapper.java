package com.example.BankDocumentManagementSystem.util.mapper;

import com.example.BankDocumentManagementSystem.dto.responce.CommentDTOResp;
import com.example.BankDocumentManagementSystem.dto.responce.DocumentDTOResp;
import com.example.BankDocumentManagementSystem.dto.responce.PostDTOResp;
import com.example.BankDocumentManagementSystem.dto.responce.UserDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DocumentMapper extends BaseMapper<Document, DocumentDTOResp> {
    @Override
    public DocumentDTOResp toDTO(Document document) {
//        UserDTOResp userDTOResp = userMapper.toDTO(document.getUser());

        Set<CommentDTOResp> commentDTOResps = document.getComments().stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toSet());

        PostDTOResp postDTOResps = postMapper.toDTO(document.getPost());

        return DocumentDTOResp.builder()
                .id(document.getId())
                .url(document.getUrl())
                .fileName(document.getOriginalFileName())
//                .user(userDTOResp)
                .comments(commentDTOResps)
                .post(postDTOResps)
                .build();
    }
}
