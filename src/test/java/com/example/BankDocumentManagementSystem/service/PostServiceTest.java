package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.exception.custom_exception.DocumentFailedException;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.entity.Post;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.PostRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.util.WebClientMethods;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    @Mock
    private PostRepo postRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private DocumentRepo documentRepo;

    @Mock
    private WebClientMethods<PostService.PostRes> webClientMethods;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_ValidDocument_SuccessfullyCreated() {
        // Arrange
        String fileName = "filename";
        String userName = "hana";
        String title = "Test Title";
        String body = "Test Body";
        DocumentParam documentParam = new DocumentParam(fileName, userName);

        Document document = new Document();
        document.setId(1);
        document.setOriginalFileName(fileName);

        User user = new User();
        user.setId(1);
        user.setUserName(userName);
        user.getDocuments().add(document);

        PostService.PostRes postRes = new PostService.PostRes(title, body, 1, 1);

        when(userRepo.findByUserName(any())).thenReturn(Optional.of(user));
        when(documentRepo.save(any(Document.class))).thenReturn(document);
        when(webClientMethods.post(anyString(), anyString(), any(), eq(PostService.PostRes.class))).thenReturn(Mono.just(postRes));
        postService.setWebClientMethods(webClientMethods);

        // Act
        assertDoesNotThrow(() -> postService.create(title, body, documentParam));

        // Assert
        verify(userRepo, times(1)).findByUserName(documentParam.userName());
        verify(documentRepo, times(1)).save(any(Document.class));
        verify(postRepo, times(1)).save(any(Post.class));
    }

    @Test
    void testCreate_DocumentNotExists_ThrowsException() {
        // Arrange
        String fileName = "filename";
        String userName = "hana";
        String title = "Test Title";
        String body = "Test Body";
        DocumentParam documentParam = new DocumentParam(userName, userName);

        User user = new User();
        user.setDocuments(new HashSet<>());

        when(userRepo.findByUserName(documentParam.userName())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(DocumentFailedException.class, () -> postService.create(title, body, documentParam));

        verify(userRepo, times(1)).findByUserName(documentParam.userName());
        verify(documentRepo, never()).save(any(Document.class));
        verify(postRepo, never()).save(any(Post.class));
    }

    @Test
    void testFindByDocumentName_ValidDocument_ReturnsPost() {
        // Arrange
        String fileName = "filename";
        String userName = "hana";
        String title = "Test Title";
        String body = "Test Body";
        DocumentParam documentParam = new DocumentParam(fileName, userName);

        User user = new User();
        user.setUserName(userName);
        user.setDocuments(new HashSet<>());

        Document document = new Document();
        document.setOriginalFileName(fileName);

        Post post = new Post();
        post.setId(1);
        document.setPost(post);

        user.getDocuments().add(document);

        PostService.PostRes postRes = new PostService.PostRes(title, body, 1, 1);

        when(userRepo.findByUserName(documentParam.userName())).thenReturn(Optional.of(user));
        when(webClientMethods.get(anyString(), anyString(), anyInt(), eq(PostService.PostRes.class))).thenReturn(Mono.just(postRes));
        postService.setWebClientMethods(webClientMethods);

        // Act
        PostService.PostRes result = postService.findByDocumentName(documentParam);

        // Assert
        assertNotNull(result);
        assertEquals(title, result.title());
        assertEquals(body, result.body());
        assertEquals(1, result.userId());
        assertEquals(1, result.id());
        verify(userRepo, times(1)).findByUserName(documentParam.userName());
        verify(webClientMethods, times(1)).get(anyString(), anyString(), anyInt(), eq(PostService.PostRes.class));
    }
}
