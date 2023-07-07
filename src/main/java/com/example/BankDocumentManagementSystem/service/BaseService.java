package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.util.mapper.BaseMapper;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BaseService <Entity, Repo extends JpaRepository<Entity, ID>, ID, DTO, Mapper extends BaseMapper<Entity, DTO>>{

    @Autowired
    private ObjectFactory<Repo> repo;
    @Autowired
    private ObjectFactory<Mapper> mapper;

    public DTO findById(ID id){
        return getRepo().findById(id).map(getMapper()::toDTO).orElseThrow();
    }
    public void removeById(ID id){
        getRepo().deleteById(id);
    }
    public List<DTO> findAll(){
        return getRepo().findAll().stream().map(getMapper()::toDTO).collect(Collectors.toList());
    }

//    public Entity save(Entity entity){
//        return repo.save(entity);
//    }

    protected Repo getRepo(){
        return repo.getObject();
    }
    protected Mapper getMapper(){
        return mapper.getObject();
    }
}
