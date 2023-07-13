package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.service.DocumentService;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(DocumentController.class)
class DocumentControllerTest {

    @MockBean
    private DocumentService documentService;

    @MockBean
    private DocumentRepo documentRepo;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testDelete_ValidDocument_ReturnsSuccess() throws Exception {
        // Arrange
        String userName = "User";
        String fileName = "Document";

        doNothing().when(documentService).delete(any(DocumentParam.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/document/delete")
                        .param("username", userName)
                        .param("filename", fileName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        verify(documentService, times(1)).delete(any(DocumentParam.class));
    }

    @Test
    void testUpload_ValidFile_ReturnsCreatedStatus() throws Exception {
        // Arrange
        String userName = "User";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        doNothing().when(documentService).upload(any(DocumentParam.class), any(InputStream.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/document/upload")
                        .file(file)
                        .param("username", userName))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        verify(documentService, times(1)).upload(any(DocumentParam.class), any(InputStream.class));
    }

    @Test
    void testUpdate_ValidFile_ReturnsCreatedStatus() throws Exception {
        // Arrange
        String userName = "User";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        doNothing().when(documentService).update(any(DocumentParam.class), any(InputStream.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/document/update")
                        .file(file)
                        .param("username", userName))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        verify(documentService, times(1)).update(any(DocumentParam.class), any(InputStream.class));
    }

    @Test
    void testDownload_ValidDocument_ReturnsFile() throws Exception {
        // Arrange
        String userName = "User";
        String fileName = "Document";
        Resource resource = new ByteArrayResource("Hello, World!".getBytes());

        when(documentService.download(any(DocumentParam.class))).thenReturn(resource);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/document/download")
                        .param("username", userName)
                        .param("filename", fileName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Document"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(MockMvcResultMatchers.content().string("Hello, World!"));

        verify(documentService, times(1)).download(any(DocumentParam.class));
    }
}
