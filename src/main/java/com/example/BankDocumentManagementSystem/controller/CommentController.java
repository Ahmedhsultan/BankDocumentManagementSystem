package com.example.BankDocumentManagementSystem.controller;

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
public class CommentController extends BaseController<Integer, Comment, CommentRepo, CommentDTOResp, CommentMapper, CommentService> {
    private CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestParam("comment") String body,
                                         @RequestParam("title") String title,
                                         @RequestParam("document") String documentName,
                                         @RequestParam("user") String userName ) {

        DocumentParam documentParam = new DocumentParam(documentName, userName);
        commentService.create(title, body, documentParam);

        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }
    @GetMapping("view")
    public ResponseEntity<List<CommentService.CommentRes>> view(@RequestParam("document") String documentName,
                                                          @RequestParam("user") String userName){

        DocumentParam documentParam = new DocumentParam(documentName, userName);
        List<CommentService.CommentRes> commentDTOResp = commentService.findByDocumentName(documentParam);

        return ResponseEntity.ok(commentDTOResp);
    }
}
