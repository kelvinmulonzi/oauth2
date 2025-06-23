package com.example.Oath2.controller;

import com.example.Oath2.dto.OAuth2ClientDto;
import com.example.Oath2.dto.OAuth2ClientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import com.example.Oath2.dto.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@PreAuthorize("hasRole('ADMIN')")
public class OAuth2ClientController {

    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody OAuth2ClientDto clientDto) {
        try {
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId(clientDto.getClientId())
                    .clientSecret(passwordEncoder.encode(clientDto.getClientSecret()))
                    .clientName(clientDto.getClientName())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUris(uris -> uris.addAll(clientDto.getRedirectUris()))
                    .scopes(scopes -> scopes.addAll(clientDto.getScopes()))
                    .clientSettings(ClientSettings.builder()
                            .requireAuthorizationConsent(true)
                            .build())
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenTimeToLive(Duration.ofSeconds(clientDto.getAccessTokenTimeToLive()))
                            .refreshTokenTimeToLive(Duration.ofSeconds(clientDto.getRefreshTokenTimeToLive()))
                            .build())
                    .build();

            registeredClientRepository.save(registeredClient);
            return ResponseEntity.ok(new OAuth2ClientResponseDto(registeredClient));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<OAuth2ClientResponseDto> getClient(@PathVariable String clientId) {
        RegisteredClient client = registeredClientRepository.findByClientId(clientId);
        if (client != null) {
            return ResponseEntity.ok(new OAuth2ClientResponseDto(client));
        }
        return ResponseEntity.notFound().build();
    }
}