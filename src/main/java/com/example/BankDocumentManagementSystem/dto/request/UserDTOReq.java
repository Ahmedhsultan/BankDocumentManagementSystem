package com.example.BankDocumentManagementSystem.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOReq {
    private String name;
    private Integer age;
    private String userName;
    private String password;
}
