package com.example.BankDocumentManagementSystem.util.mapper;

import com.example.BankDocumentManagementSystem.dto.responce.CommentDTOResp;
import com.example.BankDocumentManagementSystem.dto.responce.PostDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.Post;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PostMapper extends BaseMapper <Post, PostDTOResp> {
    @Override
    public PostDTOResp toDTO(Post post) {
        Set<CommentDTOResp> commentDTOResps = post.getComments().stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toSet());


        return PostDTOResp.builder()
                .id(post.getId())
                .comments(commentDTOResps)
                .build();
    }
}
