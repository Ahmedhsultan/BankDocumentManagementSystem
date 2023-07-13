package com.example.BankDocumentManagementSystem.integration;

import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.PostRepo;
import com.example.BankDocumentManagementSystem.service.PostService;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.event.DocumentEvent;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class PostControllerIntegrationTest {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private PostService postService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void test_create_post() throws Exception {
        // Arrange
        String body = "Test Body";
        String title = "Test Title";
        String documentName = "Document";
        String userName = "ahmed";
        DocumentParam documentParam = new DocumentParam(documentName, userName);

        User user = testEntityManager.find(User.class, 1);
        userName = user.getUserName();

        Document document = new Document();
        document.setOriginalFileName(documentName);
        document.setFileHash("c132cee4c5d311c55e8081a49fd915e7");
        user.getDocuments().add(document);
        testEntityManager.persist(user);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/post/create")
                        .param("post", body)
                        .param("title", title)
                        .param("document", documentName)
                        .param("user", userName))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }
}
