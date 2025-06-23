package com.example.Oath2.service;

import com.example.Oath2.entities.OAuth2ClientEntity;
import com.example.Oath2.repos.OAuth2ClientRepository;
import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.stream.Collectors;

@Service
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    @Autowired
    private OAuth2ClientRepository clientRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        OAuth2ClientEntity entity = toEntity(registeredClient);
        clientRepository.save(entity);
    }

    @Override
    public RegisteredClient findById(String id) {
        return clientRepository.findById(id)
                .map(this::toObject)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(this::toObject)
                .orElse(null);
    }

    private OAuth2ClientEntity toEntity(RegisteredClient registeredClient) {
        OAuth2ClientEntity entity = new OAuth2ClientEntity();
        entity.setClientId(registeredClient.getClientId());
        entity.setClientSecret(registeredClient.getClientSecret());
        entity.setClientName(registeredClient.getClientName());
        entity.setRedirectUris(registeredClient.getRedirectUris());
        entity.setScopes(registeredClient.getScopes());
        entity.setGrantTypes(registeredClient.getAuthorizationGrantTypes().stream()
                .map(AuthorizationGrantType::getValue).collect(Collectors.toSet()));
        entity.setAuthenticationMethods(registeredClient.getClientAuthenticationMethods().stream()
                .map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet()));
        entity.setAccessTokenTimeToLive((int) registeredClient.getTokenSettings()
                .getAccessTokenTimeToLive().toSeconds());
        entity.setRefreshTokenTimeToLive((int) registeredClient.getTokenSettings()
                .getRefreshTokenTimeToLive().toSeconds());
        return entity;
    }

    private RegisteredClient toObject(OAuth2ClientEntity entity) {
        return RegisteredClient.withId(entity.getClientId())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .clientName(entity.getClientName())
                .redirectUris(uris -> uris.addAll(entity.getRedirectUris()))
                .scopes(scopes -> scopes.addAll(entity.getScopes()))
                .authorizationGrantTypes(grantTypes -> {
                    entity.getGrantTypes().forEach(grantType ->
                            grantTypes.add(new AuthorizationGrantType(grantType)));
                })
                .clientAuthenticationMethods(authMethods -> {
                    entity.getAuthenticationMethods().forEach(authMethod ->
                            authMethods.add(new ClientAuthenticationMethod(authMethod)));
                })
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofSeconds(entity.getAccessTokenTimeToLive()))
                        .refreshTokenTimeToLive(Duration.ofSeconds(entity.getRefreshTokenTimeToLive()))
                        .build())
                .build();
    }
}
