package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.dto.responce.UserDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.service.UserService;
import com.example.BankDocumentManagementSystem.util.mapper.UserMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<Integer, User, UserRepo, UserDTOResp, UserMapper, UserService>{
}
