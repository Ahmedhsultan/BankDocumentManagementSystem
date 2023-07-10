package com.example.BankDocumentManagementSystem.dto.responce;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PostDTOResp {
    private Integer id;
    private DocumentDTOResp document;
    private Set<CommentDTOResp> comments;
}
