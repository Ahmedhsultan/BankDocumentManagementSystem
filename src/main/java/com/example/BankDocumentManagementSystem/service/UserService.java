package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.dto.UserDTO;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.util.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, UserRepo, Integer, UserDTO, UserMapper> {
}
