package com.example.Oath2.repos;

import com.example.Oath2.entities.OAuth2AuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuth2AuthorizationRepository extends JpaRepository<OAuth2AuthorizationEntity, String> {
    Optional<OAuth2AuthorizationEntity> findByState(String state);
    Optional<OAuth2AuthorizationEntity> findByAuthorizationCodeValue(String authorizationCode);
    Optional<OAuth2AuthorizationEntity> findByAccessTokenValue(String accessToken);
    Optional<OAuth2AuthorizationEntity> findByRefreshTokenValue(String refreshToken);

    @Query("SELECT a FROM OAuth2AuthorizationEntity a WHERE " +
            "a.state = :token OR a.authorizationCodeValue = :token OR " +
            "a.accessTokenValue = :token OR a.refreshTokenValue = :token")
    Optional<OAuth2AuthorizationEntity> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(@Param("token") String token);
}

