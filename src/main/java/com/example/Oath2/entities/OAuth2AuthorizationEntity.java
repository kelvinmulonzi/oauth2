package com.example.Oath2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.time.Instant;

@Entity
@Table(name = "oauth2_authorizations")
public class OAuth2AuthorizationEntity {
    @Id
    private String id;

    @Column(name = "registered_client_id")
    private String registeredClientId;

    @Column(name = "principal_name")
    private String principalName;

    @Column(name = "authorization_grant_type")
    private String authorizationGrantType;

    @Column(name = "authorized_scopes", length = 1000)
    private String authorizedScopes;

    private String state;

    @Column(name = "authorization_code_value")
    private String authorizationCodeValue;

    @Column(name = "authorization_code_issued_at")
    private Instant authorizationCodeIssuedAt;

    @Column(name = "authorization_code_expires_at")
    private Instant authorizationCodeExpiresAt;

    @Column(name = "access_token_value")
    private String accessTokenValue;

    @Column(name = "access_token_issued_at")
    private Instant accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private Instant accessTokenExpiresAt;

    @Column(name = "access_token_type")
    private String accessTokenType;

    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @Column(name = "refresh_token_value")
    private String refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;

    // Constructors, getters, setters
    public OAuth2AuthorizationEntity() {}

    // All getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public RegisteredClient getRegisteredClientId() { return registeredClientId; }
    public void setRegisteredClientId(String registeredClientId) { this.registeredClientId = registeredClientId; }

    public String getPrincipalName() { return principalName; }
    public void setPrincipalName(String principalName) { this.principalName = principalName; }

    public String getAuthorizationGrantType() { return authorizationGrantType; }
    public void setAuthorizationGrantType(String authorizationGrantType) { this.authorizationGrantType = authorizationGrantType; }

    public String getAuthorizedScopes() { return authorizedScopes; }
    public void setAuthorizedScopes(String authorizedScopes) { this.authorizedScopes = authorizedScopes; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getAuthorizationCodeValue() { return authorizationCodeValue; }
    public void setAuthorizationCodeValue(String authorizationCodeValue) { this.authorizationCodeValue = authorizationCodeValue; }

    public Instant getAuthorizationCodeIssuedAt() { return authorizationCodeIssuedAt; }
    public void setAuthorizationCodeIssuedAt(Instant authorizationCodeIssuedAt) { this.authorizationCodeIssuedAt = authorizationCodeIssuedAt; }

    public Instant getAuthorizationCodeExpiresAt() { return authorizationCodeExpiresAt; }
    public void setAuthorizationCodeExpiresAt(Instant authorizationCodeExpiresAt) { this.authorizationCodeExpiresAt = authorizationCodeExpiresAt; }

    public String getAccessTokenValue() { return accessTokenValue; }
    public void setAccessTokenValue(String accessTokenValue) { this.accessTokenValue = accessTokenValue; }

    public Instant getAccessTokenIssuedAt() { return accessTokenIssuedAt; }
    public void setAccessTokenIssuedAt(Instant accessTokenIssuedAt) { this.accessTokenIssuedAt = accessTokenIssuedAt; }

    public Instant getAccessTokenExpiresAt() { return accessTokenExpiresAt; }
    public void setAccessTokenExpiresAt(Instant accessTokenExpiresAt) { this.accessTokenExpiresAt = accessTokenExpiresAt; }

    public String getAccessTokenType() { return accessTokenType; }
    public void setAccessTokenType(String accessTokenType) { this.accessTokenType = accessTokenType; }

    public String getAccessTokenScopes() { return accessTokenScopes; }
    public void setAccessTokenScopes(String accessTokenScopes) { this.accessTokenScopes = accessTokenScopes; }

    public String getRefreshTokenValue() { return refreshTokenValue; }
    public void setRefreshTokenValue(String refreshTokenValue) { this.refreshTokenValue = refreshTokenValue; }

    public Instant getRefreshTokenIssuedAt() { return refreshTokenIssuedAt; }
    public void setRefreshTokenIssuedAt(Instant refreshTokenIssuedAt) { this.refreshTokenIssuedAt = refreshTokenIssuedAt; }

    public Instant getRefreshTokenExpiresAt() { return refreshTokenExpiresAt; }
    public void setRefreshTokenExpiresAt(Instant refreshTokenExpiresAt) { this.refreshTokenExpiresAt = refreshTokenExpiresAt; }
}
