package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.util.mapper.BaseMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BaseService <Entity, Repo extends JpaRepository<Entity, ID>, ID, DTO, Mapper extends BaseMapper<Entity, DTO>>{

    private Repo repo;
    private Mapper mapper;

    public BaseService(Repo repo, Mapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public DTO findById(ID id){
        return repo.findById(id).map(mapper::toDTO).orElseThrow();
    }
    public void removeById(ID id){
        repo.deleteById(id);
    }
    public List<DTO> findAll(){
        return repo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    public Entity save(Entity entity){
        return repo.save(entity);
    }
}
