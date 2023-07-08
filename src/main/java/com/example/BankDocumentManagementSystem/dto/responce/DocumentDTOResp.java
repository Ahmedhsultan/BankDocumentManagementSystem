package com.example.BankDocumentManagementSystem.dto.responce;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
@Data
@Builder
public class DocumentDTOResp {
    private Integer id;
    private String url;
    private String fileName;
    private UserDTOResp user;
    private Set<PostDTOResp> posts;
    private Set<CommentDTOResp> comments;
}
