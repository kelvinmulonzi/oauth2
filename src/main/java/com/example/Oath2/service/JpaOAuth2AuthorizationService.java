package com.example.Oath2.service;

import com.example.Oath2.entities.OAuth2AuthorizationEntity;
import com.example.Oath2.repos.OAuth2AuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class JpaOAuth2AuthorizationService implements OAuth2AuthorizationService {

    @Autowired
    private OAuth2AuthorizationRepository authorizationRepository;

    @Override
    public void save(OAuth2Authorization authorization) {
        OAuth2AuthorizationEntity entity = toEntity(authorization);
        authorizationRepository.save(entity);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        authorizationRepository.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return authorizationRepository.findById(id)
                .map(this::toObject)
                .orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Optional<OAuth2AuthorizationEntity> result = Optional.empty();

        if (tokenType == null) {
            result = authorizationRepository.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            result = authorizationRepository.findByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            result = authorizationRepository.findByAuthorizationCodeValue(token);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            result = authorizationRepository.findByAccessTokenValue(token);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            result = authorizationRepository.findByRefreshTokenValue(token);
        }

        return result.map(this::toObject).orElse(null);
    }

    private OAuth2AuthorizationEntity toEntity(OAuth2Authorization authorization) {
        OAuth2AuthorizationEntity entity = new OAuth2AuthorizationEntity();
        entity.setId(authorization.getId());
        entity.setRegisteredClientId(authorization.getRegisteredClientId());
        entity.setPrincipalName(authorization.getPrincipalName());
        entity.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        entity.setAuthorizedScopes(String.join(",", authorization.getAuthorizedScopes()));
        entity.setState(authorization.getAttribute(OAuth2ParameterNames.STATE));

        // Handle authorization code
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode =
                authorization.getToken(OAuth2AuthorizationCode.class);
        if (authorizationCode != null) {
            entity.setAuthorizationCodeValue(authorizationCode.getToken().getTokenValue());
            entity.setAuthorizationCodeIssuedAt(authorizationCode.getToken().getIssuedAt());
            entity.setAuthorizationCodeExpiresAt(authorizationCode.getToken().getExpiresAt());
        }

        // Handle access token
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken =
                authorization.getToken(OAuth2AccessToken.class);
        if (accessToken != null) {
            entity.setAccessTokenValue(accessToken.getToken().getTokenValue());
            entity.setAccessTokenIssuedAt(accessToken.getToken().getIssuedAt());
            entity.setAccessTokenExpiresAt(accessToken.getToken().getExpiresAt());
            entity.setAccessTokenType(accessToken.getToken().getTokenType().getValue());
            entity.setAccessTokenScopes(String.join(",", accessToken.getToken().getScopes()));
        }

        // Handle refresh token
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken =
                authorization.getToken(OAuth2RefreshToken.class);
        if (refreshToken != null) {
            entity.setRefreshTokenValue(refreshToken.getToken().getTokenValue());
            entity.setRefreshTokenIssuedAt(refreshToken.getToken().getIssuedAt());
            entity.setRefreshTokenExpiresAt(refreshToken.getToken().getExpiresAt());
        }

        return entity;
    }

    private OAuth2Authorization toObject(OAuth2AuthorizationEntity entity) {
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(entity.getRegisteredClientId())
                .id(entity.getId())
                .principalName(entity.getPrincipalName())
                .authorizationGrantType(new AuthorizationGrantType(entity.getAuthorizationGrantType()))
                .authorizedScopes(Set.of(entity.getAuthorizedScopes().split(",")));

        if (entity.getState() != null) {
            builder.attribute(OAuth2ParameterNames.STATE, entity.getState());
        }

        // Build authorization code token
        if (entity.getAuthorizationCodeValue() != null) {
            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(
                    entity.getAuthorizationCodeValue(),
                    entity.getAuthorizationCodeIssuedAt(),
                    entity.getAuthorizationCodeExpiresAt()
            );
            builder.token(authorizationCode);
        }

        // Build access token
        if (entity.getAccessTokenValue() != null) {
            OAuth2AccessToken accessToken = new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.valueOf(entity.getAccessTokenType().toUpperCase()),
                    entity.getAccessTokenValue(),
                    entity.getAccessTokenIssuedAt(),
                    entity.getAccessTokenExpiresAt(),
                    Set.of(entity.getAccessTokenScopes().split(","))
            );
            builder.token(accessToken);
        }

        // Build refresh token
        if (entity.getRefreshTokenValue() != null) {
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    entity.getRefreshTokenValue(),
                    entity.getRefreshTokenIssuedAt(),
                    entity.getRefreshTokenExpiresAt()
            );
            builder.token(refreshToken);
        }

        return builder.build();
    }
}
