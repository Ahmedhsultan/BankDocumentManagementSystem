package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.persistence.repository.PostRepo;
import com.example.BankDocumentManagementSystem.service.PostService;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;


@WebMvcTest(PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

    @MockBean
    private PostRepo postRepo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreate_ValidPost_ReturnsCreatedStatus() throws Exception {
        // Arrange
        String body = "Test Body";
        String title = "Test Title";
        String documentName = "Document";
        String userName = "User";
        DocumentParam documentParam = new DocumentParam(documentName, userName);

        doNothing().when(postService).create(title, body, documentParam);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/post/create")
                        .param("post", body)
                        .param("title", title)
                        .param("document", documentName)
                        .param("user", userName))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        verify(postService, times(1)).create(title, body, documentParam);
    }

    @Test
    void testView_ValidDocument_ReturnsPost() throws Exception {
        // Arrange
        String documentName = "Document";
        String userName = "User";
        DocumentParam documentParam = new DocumentParam(documentName, userName);

        PostService.PostRes postRes = new PostService.PostRes("Title", "Body", 1, 1);

        when(postService.findByDocumentName(documentParam)).thenReturn(postRes);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/post/view")
                        .param("document", documentName)
                        .param("user", userName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"title\":\"Title\",\"body\":\"Body\",\"userId\":1,\"id\":1}"));

        verify(postService, times(1)).findByDocumentName(documentParam);
    }
}
