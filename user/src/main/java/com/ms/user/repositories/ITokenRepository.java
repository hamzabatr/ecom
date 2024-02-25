package com.ms.user.repositories;

import com.ms.user.configurations.jwt.EToken;
import com.ms.user.entities.Token;
import com.ms.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<Token, String> {
    boolean existsByTokenAndType(String token, EToken type);
    Optional<Token> findTokenByTokenAndType(String token, EToken type);
    void deleteTokenByTokenAndType(String token, EToken type);
    void deleteTokenByUserAndType(User user, EToken type);
}
