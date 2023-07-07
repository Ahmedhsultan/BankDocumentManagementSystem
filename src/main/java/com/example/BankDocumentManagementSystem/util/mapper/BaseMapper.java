package com.example.BankDocumentManagementSystem.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMapper <Entity, DTO>{

    @Autowired
    protected CommentMapper commentMapper;
    @Autowired
    protected DocumentMapper documentMapper;
    @Autowired
    protected PostMapper postMapper;
    @Autowired
    protected UserMapper userMapper;

    public abstract DTO toDTO(Entity entity);
}
