package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.MockFileResource;
import com.example.BankDocumentManagementSystem.exception.custom_exception.DocumentFailedException;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DocumentServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpload_InvalidDocument_ThrowsException() {
        // Arrange
        DocumentParam documentParam = new DocumentParam("filename", "username");

        User user = new User();
        user.setUserName("username");

        InputStream inputStream = new ByteArrayInputStream("Test data".getBytes());

        MessageDigest messageDigest = mock(MessageDigest.class);
        documentService.setMessageDigest(messageDigest);
        when(messageDigest.digest(any(byte[].class))).thenThrow(new DocumentFailedException());
        when(userRepo.findByUserName(documentParam.userName())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(DocumentFailedException.class, () -> documentService.upload(documentParam, inputStream));
    }
    @Test
    void testDownload_DocumentExists_ReturnsResource() {
        // Arrange
        String fileName = "filename";
        String userName = "hana";
        DocumentParam documentParam = new DocumentParam(fileName, userName);

        User user = new User();
        user.setUserName(userName);

        Set<Document> documents = new HashSet<>();

        Document document = new Document();
        document.setFileHash("filehash");
        document.setOriginalFileName(fileName);

        documents.add(document);
        user.setDocuments(documents);


        MessageDigest messageDigest = mock(MessageDigest.class);
        documentService.setMessageDigest(messageDigest);
        when(messageDigest.digest(any())).thenReturn("filehash".getBytes());
        when(userRepo.findByUserName(documentParam.userName())).thenReturn(Optional.of(user));
        documentService.setFileSystemResourceClass(MockFileResource.class);

        // Act
        Resource result = documentService.download(documentParam);

        // Assert
        assertEquals("filename", result.getFilename());
    }

    @Test
    void testUpload_ValidDocument_SuccessfullyUploaded() {
        // Arrange
        DocumentParam documentParam = new DocumentParam("filename", "username");

        User user = new User();
        user.setUserName("username");

        InputStream inputStream = new ByteArrayInputStream("Test data".getBytes());

        MessageDigest messageDigest = mock(MessageDigest.class);
        documentService.setMessageDigest(messageDigest);
        when(messageDigest.digest(any())).thenReturn("Test data".getBytes());
        when(userRepo.findByUserName(documentParam.userName())).thenReturn(Optional.of(user));
        documentService.setFileSystemResourceClass(MockFileResource.class);

        // Act
        assertDoesNotThrow(() -> documentService.upload(documentParam, inputStream));
        Document document = user.getDocuments().stream().findFirst().get();

        // Assert
        assertEquals("filename", document.getOriginalFileName());
        assertEquals(new BigInteger(1, "Test data".getBytes()).toString(16), document.getFileHash());
    }
}