package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.persistence.repository.CommentRepo;
import com.example.BankDocumentManagementSystem.service.CommentService;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentRepo commentRepo;

    @Autowired
    private MockMvc mockMvc;



    @Test
    void testCreate_ValidComment_ReturnsCreatedStatus() throws Exception {
        // Arrange
        String body = "Test Body";
        String title = "Test Title";
        String documentName = "Document";
        String userName = "User";
        DocumentParam documentParam = new DocumentParam(documentName, userName);

        doNothing().when(commentService).create(title, body, documentParam);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/create")
                        .param("comment", body)
                        .param("title", title)
                        .param("document", documentName)
                        .param("user", userName))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        verify(commentService, times(1)).create(title, body, documentParam);
    }

    @Test
    void testView_ValidDocument_ReturnsComments() throws Exception {
        // Arrange
        String documentName = "Document";
        String userName = "User";
        DocumentParam documentParam = new DocumentParam(documentName, userName);

        CommentService.CommentRes commentRes1 = new CommentService.CommentRes("Title1", "Body1", 1, 1);
        CommentService.CommentRes commentRes2 = new CommentService.CommentRes("Title2", "Body2", 2, 2);
        List<CommentService.CommentRes> commentList = Arrays.asList(commentRes1, commentRes2);

        when(commentService.findByDocumentName(documentParam)).thenReturn(commentList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/comment/view")
                        .param("document", documentName)
                        .param("user", userName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"title\":\"Title1\",\"body\":\"Body1\",\"userId\":1,\"id\":1},{\"title\":\"Title2\",\"body\":\"Body2\",\"userId\":2,\"id\":2}]"));

        verify(commentService, times(1)).findByDocumentName(documentParam);
    }
}
