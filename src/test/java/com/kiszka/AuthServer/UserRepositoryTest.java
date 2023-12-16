package com.kiszka.AuthServer;

import com.kiszka.AuthServer.dbconnection.User;
import com.kiszka.AuthServer.dbconnection.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmailAddress() {
        // Given
        User user = new User();
        user.setEmailAddress("qwerty123@gmail.com");
        user.setPassword("password");
        user.setRole("ROLE_USER");

        entityManager.persist(user);
        entityManager.flush();

        // When
        Optional<User> foundUserOptional = userRepository.findByEmailAddress("qwerty123@gmail.com");

        // Then
        Assertions.assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        Assertions.assertEquals("qwerty123@gmail.com", foundUser.getEmailAddress());
        Assertions.assertEquals("password", foundUser.getPassword());
        Assertions.assertEquals("ROLE_USER", foundUser.getRole());
    }
}
