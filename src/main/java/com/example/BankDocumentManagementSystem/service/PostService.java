package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.dto.request.PostDTOReq;
import com.example.BankDocumentManagementSystem.dto.responce.PostDTOResp;
import com.example.BankDocumentManagementSystem.exception.custom_exception.DocumentFailedException;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.entity.Post;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.PostRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.util.WebClientMethods;
import com.example.BankDocumentManagementSystem.util.mapper.PostMapper;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Service
public class PostService extends BaseService<PostRepo, Integer, PostDTOResp, PostMapper> {
    private String apiURL = "https://jsonplaceholder.typicode.com";
    private PostRepo postRepo;
    private UserRepo userRepo;
    private DocumentRepo documentRepo;
    private WebClientMethods<PostRes> webClientMethods;
    public PostService(PostRepo postRepo, UserRepo userRepo, DocumentRepo documentRepo){
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.documentRepo = documentRepo;
        this.webClientMethods = new WebClientMethods<>();
    }

    public void create(PostDTOReq postDTOReq) {
        //Get user and get document from this user
        User user = getUser(postDTOReq.userName());
        Set<Document> documents = user.getDocuments();

        Optional<Document> optionalDocument = documents.stream()
                .filter(x -> x.getOriginalFileName().equals(postDTOReq.documentName()))
                .findFirst();

        if (!optionalDocument.isPresent())
            throw new DocumentFailedException("Document not exist!!");

        //Get id of post related to this document
        Document document = optionalDocument.get();

        //Save post in 3rd part
        Mono<PostRes> response = getWebClient().post(apiURL,
                "/posts", new PostReq(postDTOReq.title(), postDTOReq.body(), user.getId())
                        , PostRes.class);

        int postId = response.block().id;

        //Add post to document
        savePostInDatabase(postId, document, user);
    }
    public PostRes findByDocumentName(DocumentParam documentParam) {
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
        Integer postId = document.getPost().getId();

        //Get post from 3rd part
        Mono<PostRes> response = getWebClient().get(apiURL, "/posts/{postId}",
                postId
                , PostRes.class);

        return response.block();
    }

    public record PostReq (String title, String body, int userId){}
    public record PostRes (String title, String body, int userId, int id){}
    private void savePostInDatabase(int postId, Document document, User user){
        Post post = Post.builder()
                .id(postId)
                .build();

        postRepo.save(post);

        document.setPost(post);
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
