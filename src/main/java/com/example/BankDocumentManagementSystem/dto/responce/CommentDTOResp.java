package com.example.BankDocumentManagementSystem.dto.responce;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class CommentDTOResp {
    private Integer id;
    private PostDTOResp post;
    private Set<DocumentDTOResp> documents;
}
