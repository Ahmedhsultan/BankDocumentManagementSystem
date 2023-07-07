package com.example.BankDocumentManagementSystem.util.mapper;

import com.example.BankDocumentManagementSystem.dto.responce.CommentDTOResp;
import com.example.BankDocumentManagementSystem.dto.responce.DocumentDTOResp;
import com.example.BankDocumentManagementSystem.dto.responce.PostDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommentMapper extends BaseMapper<Comment, CommentDTOResp>{
    @Override
    public CommentDTOResp toDTO(Comment comment) {

        Set<DocumentDTOResp> documentDTOResps = comment.getDocuments().stream()
                .map(documentMapper::toDTO)
                .collect(Collectors.toSet());

        PostDTOResp postDTOResp = postMapper.toDTO(comment.getPost());

        return CommentDTOResp.builder()
                .id(comment.getId())
                .documents(documentDTOResps)
                .post(postDTOResp)
                .build();
    }
}
