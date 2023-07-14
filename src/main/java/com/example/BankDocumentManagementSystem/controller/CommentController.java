package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.dto.request.CommentDTOReq;
import com.example.BankDocumentManagementSystem.dto.responce.CommentDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import com.example.BankDocumentManagementSystem.persistence.repository.CommentRepo;
import com.example.BankDocumentManagementSystem.service.CommentService;
import com.example.BankDocumentManagementSystem.util.mapper.CommentMapper;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController extends BaseController<Integer, CommentDTOResp, CommentService> {
    private CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("create")
    //@HystrixCommand(fallbackMethod = "defaultGreeting")
    public ResponseEntity<String> create(@RequestBody CommentDTOReq commentDTOReq) {

        commentService.create(commentDTOReq);

        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }
    @GetMapping("view")
    //@HystrixCommand(fallbackMethod = "defaultGreeting")
    public ResponseEntity<List<CommentService.CommentRes>> view(@RequestParam("document") String documentName,
                                                          @RequestParam("user") String userName){

        DocumentParam documentParam = new DocumentParam(documentName, userName);
        List<CommentService.CommentRes> commentDTOResp = commentService.findByDocumentName(documentParam);

        return ResponseEntity.ok(commentDTOResp);
    }
}
