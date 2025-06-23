package com.example.Oath2.dto;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class OAuth2ClientDto {
    private String clientId;
    private String clientSecret;
    private String clientName;
    private Set<String> redirectUris = new HashSet<>();
    private Set<String> scopes = new HashSet<>();
    private int accessTokenTimeToLive = 3600;
    private int refreshTokenTimeToLive = 86400;

    // Constructors, getters, setters
    public OAuth2ClientDto() {}

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public Set<String> getRedirectUris() { return redirectUris; }
    public void setRedirectUris(Set<String> redirectUris) { this.redirectUris = redirectUris; }

    public Set<String> getScopes() { return scopes; }
    public void setScopes(Set<String> scopes) { this.scopes = scopes; }

    public int getAccessTokenTimeToLive() { return accessTokenTimeToLive; }
    public void setAccessTokenTimeToLive(int accessTokenTimeToLive) { this.accessTokenTimeToLive = accessTokenTimeToLive; }

    public int getRefreshTokenTimeToLive() { return refreshTokenTimeToLive; }
    public void setRefreshTokenTimeToLive(int refreshTokenTimeToLive) { this.refreshTokenTimeToLive = refreshTokenTimeToLive; }
}

class OAuth2ClientResponseDto {
    private String clientId;
    private String clientName;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private Set<String> grantTypes;
    private int accessTokenTimeToLive;
    private int refreshTokenTimeToLive;

    public OAuth2ClientResponseDto() {}

    public OAuth2ClientResponseDto(RegisteredClient client) {
        this.clientId = client.getClientId();
        this.clientName = client.getClientName();
        this.redirectUris = client.getRedirectUris();
        this.scopes = client.getScopes();
        this.grantTypes = client.getAuthorizationGrantTypes().stream()
                .map(AuthorizationGrantType::getValue).collect(Collectors.toSet());
        this.accessTokenTimeToLive = (int) client.getTokenSettings().getAccessTokenTimeToLive().toSeconds();
        this.refreshTokenTimeToLive = (int) client.getTokenSettings().getRefreshTokenTimeToLive().toSeconds();
    }

    // Getters and setters
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public Set<String> getRedirectUris() { return redirectUris; }
    public void setRedirectUris(Set<String> redirectUris) { this.redirectUris = redirectUris; }

    public Set<String> getScopes() { return scopes; }
    public void setScopes(Set<String> scopes) { this.scopes = scopes; }

    public Set<String> getGrantTypes() { return grantTypes; }
    public void setGrantTypes(Set<String> grantTypes) { this.grantTypes = grantTypes; }

    public int getAccessTokenTimeToLive() { return accessTokenTimeToLive; }
    public void setAccessTokenTimeToLive(int accessTokenTimeToLive) { this.accessTokenTimeToLive = accessTokenTimeToLive; }

    public int getRefreshTokenTimeToLive() { return refreshTokenTimeTimeToLive; }
    public void setRefreshTokenTimeToLive(int refreshTokenTimeToLive) { this.refreshTokenTimeToLive = refreshTokenTimeToLive; }
}
