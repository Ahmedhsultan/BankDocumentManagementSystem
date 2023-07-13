package com.example.BankDocumentManagementSystem.persistence.repository;

import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
class CommentRepoTest {
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
    private CommentRepo commentRepo;

    @Test
    public void test_Find_By_Id() {
        // Create a comment and save it to the H2 database
        Comment comment = Comment.builder()
                .id(1)
                .build();

        entityManager.persist(comment);
        entityManager.flush();

        // Retrieve the comment from the repository
        Optional<Comment> optionalComment = commentRepo.findById(comment.getId());

        // Assert that the comment was found
        Assertions.assertTrue(optionalComment.isPresent());
        Assertions.assertEquals(comment.getId(), optionalComment.get().getId());
    }
}