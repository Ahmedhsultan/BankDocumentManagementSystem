package com.example.BankDocumentManagementSystem.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PostDTOReq {
    private Integer id;
    private Set<DocumentDTOReq> documents;
    private Set<CommentDTOReq> comments;
}
