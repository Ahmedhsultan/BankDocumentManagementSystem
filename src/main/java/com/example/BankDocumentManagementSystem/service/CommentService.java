package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.dto.responce.CommentDTOResp;
import com.example.BankDocumentManagementSystem.exception.custom_exception.DocumentFailedException;
import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.CommentRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.PostRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.util.WebClientMethods;
import com.example.BankDocumentManagementSystem.util.mapper.CommentMapper;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService extends BaseService<Comment, CommentRepo, Integer, CommentDTOResp, CommentMapper>{
    private String apiURL = "https://jsonplaceholder.typicode.com";
    private PostRepo postRepo;
    private CommentRepo commentRepo;
    private UserRepo userRepo;
    private DocumentRepo documentRepo;
    private WebClientMethods<CommentRes> webClientMethods;
    public CommentService(PostRepo postRepo, UserRepo userRepo, DocumentRepo documentRepo, CommentRepo commentRepo){
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.documentRepo = documentRepo;
        this.commentRepo = commentRepo;
        this.webClientMethods = new WebClientMethods<>();
    }

    public void create(String title, String body, DocumentParam documentParam) {
        //Get user and get document from this user
        User user = getUser(documentParam.userName());
        Set<Document> documents = user.getDocuments();

        Optional<Document> optionalDocument = documents.stream()
                .filter(x -> x.getOriginalFileName().equals(documentParam.fileName()))
                .findFirst();

        if (!optionalDocument.isPresent())
            throw new DocumentFailedException("Document not exist!!");

        //Get id of post related to this document
        Document document = optionalDocument.get();

        //Save CommentRes in 3rd part
        Mono<CommentRes> response = getWebClient().post(apiURL,
                "/comments", new CommentReq(title, body, user.getId())
                , CommentRes.class);

        int commentId = response.block().id;

        //Add CommentRes to document
        saveCommentInDatabase(commentId, document, user);
    }
    public List<CommentRes> findByDocumentName(DocumentParam documentParam) {
        //Get user and get document from this user
        User user = getUser(documentParam.userName());
        Set<Document> documents = user.getDocuments();

        Optional<Document> optionalDocument = documents.stream()
                .filter(x -> x.getOriginalFileName().equals(documentParam.fileName()))
                .findFirst();

        if (!optionalDocument.isPresent())
            throw new DocumentFailedException("Document not exist!!");

        //Get id of CommentRes related to this document
        Document document = optionalDocument.get();
        Set<Integer> commentsId = document.getComments().stream()
                .map(x -> x.getId())
                .collect(Collectors.toSet());

        //Get comments from 3rd part
        List<CommentRes> comments = new ArrayList<>();
        for (int id : commentsId){
            Mono<CommentRes> response = getWebClient().get(apiURL, "/comments/{Id}",
                    id, CommentRes.class);
            comments.add(response.block());
        }

        return comments;
    }

    public record CommentReq (String title, String body, int userId){}
    public record CommentRes (String title, String body, int userId, int id){}
    private void saveCommentInDatabase(int commentId, Document document, User user){
        Comment comment = Comment.builder()
                .id(commentId)
                .build();

        commentRepo.save(comment);

        document.getComments().add(comment);
        documentRepo.save(document);
    }
    private User getUser(String userName){
        Optional<User> userOptional = userRepo.findByUserName(userName);
        if (!userOptional.isPresent())
            throw new DocumentFailedException("Unavailable user!!");

        return userOptional.get();
    }
    private WebClientMethods getWebClient(){
        return webClientMethods;
    }
    public void setWebClientMethods(WebClientMethods webClientMethods){
        this.webClientMethods = webClientMethods;
    }
}
