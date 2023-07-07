package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.service.BaseService;
import com.example.BankDocumentManagementSystem.util.mapper.BaseMapper;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class BaseController <ID, Entity, Repo extends JpaRepository<Entity, ID>, DTO, Mapper extends BaseMapper<Entity, DTO>, Service extends BaseService<Entity, Repo, ID, DTO, Mapper>>{
    @Autowired
    protected ObjectFactory<Service> service;

    @GetMapping("all")
    public List<DTO> getAll(){
        return getService().findAll();
    }
    @GetMapping()
    public DTO getById(@RequestParam ID id){
        return getService().findById(id);
    }

    protected Service getService(){
        return service.getObject();
    }
}
