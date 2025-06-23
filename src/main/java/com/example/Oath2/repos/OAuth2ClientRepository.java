package com.example.Oath2.repos;

import com.example.Oath2.entities.OAuth2ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2ClientRepository extends JpaRepository<OAuth2ClientEntity, String> {
    Optional<OAuth2ClientEntity> findByClientId(String clientId);
}
