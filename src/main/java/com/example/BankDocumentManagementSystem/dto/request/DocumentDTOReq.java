package com.example.BankDocumentManagementSystem.dto.request;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class DocumentDTOReq {
    private String url;
    private String fileName;
    private Integer userId;
}
