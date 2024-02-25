package com.ms.user.repositories;

import com.ms.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(String id);
    User findByUsername(String username);
    Boolean existsByUsernameOrEmail(String username, String email);
}
