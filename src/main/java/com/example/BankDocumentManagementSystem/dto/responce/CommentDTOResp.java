package com.example.BankDocumentManagementSystem.dto.responce;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentDTOResp {
    private Integer id;
    private PostDTOResp post;
    private DocumentDTOResp document;
}
