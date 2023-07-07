package com.example.BankDocumentManagementSystem.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
@Data
@Builder
public class DocumentDTOReq {
    private Byte[] bytes;
    private String fileName;
    private Integer userId;
}
