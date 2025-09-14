package com.moses.oauth2.service;

import com.moses.oauth2.dto.CreateClientRequest;
import com.moses.oauth2.repository.OAuthClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ClientManagementServiceImpl implements ClientManagementService {

    private final RegisteredClientRepository registeredClientRepository;

    private final OAuthClientRepository oAuthClientRepository;

    private final PasswordEncoder passwordEncoder;

    public RegisteredClient createClient(CreateClientRequest request) {
        String encodedSecret = passwordEncoder.encode(request.getClientId());

        RegisteredClient registeredClient = RegisteredClient.withId(request.getClientId())
                .clientId(request.getClientId()).clientSecret(encodedSecret).clientName(request.getClientName())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUris(uris -> uris.addAll(request.getRedirectUris()))
                .postLogoutRedirectUri(request.getPostLogoutRedirectUrl()).scopes(clientScopes -> {
                    clientScopes.add(OidcScopes.OPENID);
                    clientScopes.add(OidcScopes.PROFILE);
                    clientScopes.addAll(request.getScopes());
                })
                .clientSettings(
                        ClientSettings.builder().requireAuthorizationConsent(true).requireProofKey(true).build())
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(1))
                        .refreshTokenTimeToLive(Duration.ofDays(1)).reuseRefreshTokens(true).build())
                .build();

        registeredClientRepository.save(registeredClient);

        return RegisteredClient.from(registeredClient).build();
    }

    public void deleteClient(String clientId) {
        oAuthClientRepository.findByClientId(clientId).ifPresent(oAuthClientRepository::delete);
    }

    public RegisteredClient findByClientId(String clientId) {
        return registeredClientRepository.findByClientId(clientId);
    }
}
