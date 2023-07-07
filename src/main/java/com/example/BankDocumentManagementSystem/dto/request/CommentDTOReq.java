package com.example.BankDocumentManagementSystem.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class CommentDTOReq {
    private Integer id;
    private PostDTOReq post;
    private Set<DocumentDTOReq> documents;
}
