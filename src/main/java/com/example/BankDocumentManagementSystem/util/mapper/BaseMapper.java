package com.example.BankDocumentManagementSystem.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMapper <Entity, DTO>{
    public abstract DTO toDTO(Entity entity);
}
