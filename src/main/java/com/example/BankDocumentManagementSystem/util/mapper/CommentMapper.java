package com.example.BankDocumentManagementSystem.util.mapper;

import com.example.BankDocumentManagementSystem.dto.responce.CommentDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper extends BaseMapper<Comment, CommentDTOResp>{
    @Override
    public CommentDTOResp toDTO(Comment comment) {



        return CommentDTOResp.builder()
                .id(comment.getId())
                .build();
    }
}
