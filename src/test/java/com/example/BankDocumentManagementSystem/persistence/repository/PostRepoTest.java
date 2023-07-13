package com.example.BankDocumentManagementSystem.persistence.repository;

import com.example.BankDocumentManagementSystem.persistence.entity.Comment;
import com.example.BankDocumentManagementSystem.persistence.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PostRepoTest {
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
    private PostRepo postRepo;

    @Test
    void test_find_by_id() {
        // Create a post with comments and save it to the H2 database
        Post post = new Post();
        post.setId(1);

        Set<Comment> comments = new HashSet<>();
        Comment comment1 = new Comment();
        comment1.setId(1);
        comments.add(comment1);

        Comment comment2 = new Comment();
        comment2.setId(2);
        comments.add(comment2);

        post.setComments(comments);

        entityManager.persist(comment1);
        entityManager.persist(comment2);
        entityManager.persist(post);
        entityManager.flush();

        // Retrieve the post from the repository
        Optional<Post> optionalPost = postRepo.findById(post.getId());

        // Assert that the post was found and has the correct comments
        assertTrue(optionalPost.isPresent());
        Post retrievedPost = optionalPost.get();
        Assertions.assertEquals(comments.size(), retrievedPost.getComments().size());
    }
}