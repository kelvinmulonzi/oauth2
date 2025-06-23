package com.example.Oath2.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import javax.swing.text.html.parser.Entity;
import java.util.HashSet;
import java.util.Set;

Entity
@Table(name = "oauth2_clients")
public class OAuth2ClientEntity {
    @Id
    private String clientId;

    @Column(nullable = false)
    private String clientSecret;

    @Column(nullable = false)
    private String clientName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_redirect_uris")
    private Set<String> redirectUris = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_scopes")
    private Set<String> scopes = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_grant_types")
    private Set<String> grantTypes = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_auth_methods")
    private Set<String> authenticationMethods = new HashSet<>();

    private int accessTokenTimeToLive = 3600; // 1 hour
    private int refreshTokenTimeToLive = 86400; // 24 hours

    // Constructors, getters, setters
    public OAuth2ClientEntity() {}

    public OAuth2ClientEntity(String clientId, String clientSecret, String clientName) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientName = clientName;
    }

    // Getters and setters
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

    public Set<String> getGrantTypes() { return grantTypes; }
    public void setGrantTypes(Set<String> grantTypes) { this.grantTypes = grantTypes; }

    public Set<String> getAuthenticationMethods() { return authenticationMethods; }
    public void setAuthenticationMethods(Set<String> authenticationMethods) { this.authenticationMethods = authenticationMethods; }

    public int getAccessTokenTimeToLive() { return accessTokenTimeToLive; }
    public void setAccessTokenTimeToLive(int accessTokenTimeToLive) { this.accessTokenTimeToLive = accessTokenTimeToLive; }

    public int getRefreshTokenTimeToLive() { return refreshTokenTimeToLive; }
    public void setRefreshTokenTimeToLive(int refreshTokenTimeToLive) { this.refreshTokenTimeToLive = refreshTokenTimeToLive; }
}