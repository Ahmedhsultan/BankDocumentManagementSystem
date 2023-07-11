package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.dto.responce.UserDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.service.UserService;
import com.example.BankDocumentManagementSystem.util.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<Integer, User, UserRepo, UserDTOResp, UserMapper, UserService> {

    private UserService UserService;

    public UserController(UserService UserService){
        this.UserService = UserService;
    }


    @DeleteMapping("delete/username")
    public ResponseEntity<String> deleteByUserName(@RequestParam("username") String userName){
        /**
         * In case we use spring security we will get username from JWT token
         * But spring security isn't covered in this task
         * So we take username as parameter
         */

        //Delete user
        UserService.removeByUserName(userName);

        return ResponseEntity.ok("Success");
    }
    @PostMapping("create")
    public ResponseEntity<String> create(@RequestParam("username") String userName){
        UserService.create(userName);

        return ResponseEntity.ok("Success");
    }
}
