package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.service.BaseService;
import com.example.BankDocumentManagementSystem.util.mapper.BaseMapper;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class BaseController <ID, DTO, Service extends BaseService>{
    @Autowired
    protected ObjectFactory<Service> service;

    @GetMapping("all")
    public List<DTO> getAll(){
        return getService().findAll();
    }
    @GetMapping
    public DTO getById(@RequestParam ID id){
        return (DTO) getService().findById(id);
    }
    @DeleteMapping
    public void deleteById(@RequestParam ID id){
        getService().removeById(id);
    }

    protected Service getService(){
        return service.getObject();
    }
}
