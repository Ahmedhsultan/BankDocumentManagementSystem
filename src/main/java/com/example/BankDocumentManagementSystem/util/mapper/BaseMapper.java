package com.example.BankDocumentManagementSystem.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public abstract class BaseMapper <Entity, DTO>{
    @Autowired
    @Lazy
    protected CommentMapper commentMapper;
    @Autowired
    @Lazy
    protected DocumentMapper documentMapper;
    @Autowired
    @Lazy
    protected PostMapper postMapper;
    @Autowired
    @Lazy
    protected UserMapper userMapper;

    public abstract DTO toDTO(Entity entity);
}
