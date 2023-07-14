package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.dto.request.PostDTOReq;
import com.example.BankDocumentManagementSystem.dto.responce.PostDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.Post;
import com.example.BankDocumentManagementSystem.persistence.repository.PostRepo;
import com.example.BankDocumentManagementSystem.service.PostService;
import com.example.BankDocumentManagementSystem.util.mapper.PostMapper;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post")
public class PostController extends BaseController<Integer, PostDTOResp, PostService> {
    private PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("create")
    //@HystrixCommand(fallbackMethod = "defaultGreeting")
    public ResponseEntity<String> create(@RequestBody PostDTOReq postDTOReq) {

        postService.create(postDTOReq);

        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }
    @GetMapping("view")
    //@HystrixCommand(fallbackMethod = "defaultGreeting")
    public ResponseEntity<PostService.PostRes> view(@RequestParam("document") String documentName,
                                                    @RequestParam("user") String userName){

        DocumentParam documentParam = new DocumentParam(documentName, userName);
        PostService.PostRes postDTO = postService.findByDocumentName(documentParam);

        return ResponseEntity.ok(postDTO);
    }
}
