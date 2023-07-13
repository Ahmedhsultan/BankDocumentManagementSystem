package com.example.BankDocumentManagementSystem.persistence.repository;

import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DocumentRepoTest {

    /**
     * ReadMe
     * During actual testing, we utilize a non-in-memory database,
     * which means that when testing the repository, we need to create a container for the database,
     * such as "MysqlContainer," and use it in the tests. However,
     * since we are using H2 for this particular task, I will directly autowire the EntityManager.
     * It's important to note that this approach is not recommended
     */
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DocumentRepo documentRepo;

    @Test
    public void get_document_by_hashname(){
        // Create a document and save it to the H2 database
        Document document = Document.builder()
                .url("https://localhost:9090/document1")
                .fileHash("hash123")
                .originalFileName("document1.pdf")
                .build();
        entityManager.persist(document);
        entityManager.flush();

        // Retrieve the document by file hash from the repository
        List<Document> foundDocuments = documentRepo.findDocumentByFileHash("hash123");

        // Assert that the document was found and has the correct attributes
        Assertions.assertEquals(1, foundDocuments.size());
        Document foundDocument = foundDocuments.get(0);
        Assertions.assertEquals("https://localhost:9090/document1", foundDocument.getUrl());
        Assertions.assertEquals("document1.pdf", foundDocument.getOriginalFileName());
    }
}
