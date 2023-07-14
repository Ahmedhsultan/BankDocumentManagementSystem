package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.dto.responce.UserDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.util.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<UserRepo, Integer, UserDTOResp, UserMapper> {
    private UserRepo userRepo;
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public void removeByUserName(String userName) {
        userRepo.deleteByUserName(userName);
    }
    public void create(String userName) {
        User user = User.builder()
                .userName(userName)
                .build();

        userRepo.save(user);
    }
}
