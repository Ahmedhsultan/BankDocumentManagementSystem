package com.example.BankDocumentManagementSystem.dto.responce;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDTOResp {
    private Integer id;
    private String name;
    private Integer age;
    private String userName;
    private Set<DocumentDTOResp> documents;
    private Set<PostDTOResp> posts;
}
