package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.dto.request.CommentDTOReq;
import com.example.BankDocumentManagementSystem.exception.custom_exception.DocumentFailedException;
import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.CommentRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.PostRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.util.WebClientMethods;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class CommentServiceTest {

    @MockBean
    private PostRepo postRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private DocumentRepo documentRepo;

    @Mock
    private CommentRepo commentRepo;
    @Mock
    private Mono mono;

    @InjectMocks
    private CommentService commentService;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void testCreate_ValidDocument_SuccessfullyCreated() {
        // Arrange
        String fileName = "filename";
        String userName = "hana";
        String title = "Test Title";
        String body = "Test Body";
        CommentDTOReq commentDTOReq = new CommentDTOReq(body, title, fileName, userName);

        Document document = new Document();
        document.setId(1);
        document.setOriginalFileName(fileName);

        User user = new User();
        user.setId(1);
        user.setUserName(userName);
        user.getDocuments().add(document);

        CommentService.CommentRes commentRes = new CommentService.CommentRes(title, body, 1, 1);

        when(userRepo.findByUserName(any())).thenReturn(Optional.of(user));
        when(documentRepo.save(any(Document.class))).thenReturn(document);
        when(mono.block()).thenReturn(commentRes);
        when(commentRepo.save(any(Comment.class))).then(e -> testEntityManager.persist(e.getArgument(0))).thenReturn(null);
        WebClientMethods<CommentService.CommentRes> webClientMethods = mock(WebClientMethods.class);
        when(webClientMethods.post(anyString(), anyString(), any(), eq(CommentService.CommentRes.class))).thenReturn(Mono.just(commentRes));
        commentService.setWebClientMethods(webClientMethods);
        // Act
        assertDoesNotThrow(() -> commentService.create(commentDTOReq));
        Comment resultComment = testEntityManager.find(Comment.class, 1);

        // Assert.
        assertNotNull(resultComment);
        assertEquals(1, document.getComments().stream().findFirst().get().getId());
    }

    @Test
    void testCreate_DocumentNotExists_ThrowsException() {
        // Arrange
        String title = "Test Title";
        String body = "Test Body";
        String fileName = "filename";
        String userName = "hana";
        CommentDTOReq commentDTOReq = new CommentDTOReq(body, title, fileName, userName);

        User user = new User();
        user.setDocuments(new HashSet<>());
        CommentService.CommentReq commentReq = new CommentService.CommentReq(title, body, 1);

        when(userRepo.findByUserName(commentDTOReq.userName())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(DocumentFailedException.class, () -> commentService.create(commentDTOReq));

        verify(userRepo, times(1)).findByUserName(commentDTOReq.userName());
        verify(documentRepo, never()).save(any(Document.class));
        verify(commentRepo, never()).save(any(Comment.class));
    }

    @Test
    public void testFindByDocumentName_ValidDocument_ReturnsComments() {
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

        Comment comment = new Comment();
        comment.setId(1);

        document.getComments().add(comment);
        user.getDocuments().add(document);

        CommentService.CommentRes commentRes = new CommentService.CommentRes(title, body, 1, 1);

        WebClientMethods<CommentService.CommentRes> webClientMethods = mock(WebClientMethods.class);

        commentService.setWebClientMethods(webClientMethods);
        when(userRepo.findByUserName(documentParam.userName())).thenReturn(Optional.of(user));
        when(webClientMethods.get(anyString(), anyString(), anyInt(), eq(CommentService.CommentRes.class))).thenReturn(Mono.just(commentRes));

        // Act
        var result = commentService.findByDocumentName(documentParam);

        // Assert
        assertEquals(body, result.get(0).body());
        assertEquals(title, result.get(0).title());
        verify(userRepo, times(1)).findByUserName(documentParam.userName());
    }
}
