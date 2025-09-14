package com.moses.oauth2.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "oauth_clients")
@Getter
@Setter
public class OAuthClient {

    @Id
    private String id;

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @Column(name = "client_id_issued_at")
    private Instant clientIdIssuedAt;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    @Column(name = "client_name")
    private String clientName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth_client_auth_methods", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "authentication_method")
    private Set<String> clientAuthenticationMethods;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth_client_grant_types", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "authorization_grant_type")
    private Set<String> authorizationGrantTypes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth_client_redirect_uris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "redirect_uri")
    private Set<String> redirectUris;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth_client_post_logout_redirect_uris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "post_logout_redirect_uri")
    private Set<String> postLogoutRedirectUris;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth_client_scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope")
    private Set<String> scopes;

    @Column(name = "client_settings", columnDefinition = "TEXT")
    private String clientSettings;

    @Column(name = "token_settings", columnDefinition = "TEXT")
    private String tokenSettings;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        OAuthClient that = (OAuthClient) o;
        return Objects.equals(id, that.id) && Objects.equals(clientId, that.clientId)
                && Objects.equals(clientIdIssuedAt, that.clientIdIssuedAt)
                && Objects.equals(clientSecret, that.clientSecret)
                && Objects.equals(clientSecretExpiresAt, that.clientSecretExpiresAt)
                && Objects.equals(clientName, that.clientName)
                && Objects.equals(clientAuthenticationMethods, that.clientAuthenticationMethods)
                && Objects.equals(authorizationGrantTypes, that.authorizationGrantTypes)
                && Objects.equals(redirectUris, that.redirectUris)
                && Objects.equals(postLogoutRedirectUris, that.postLogoutRedirectUris)
                && Objects.equals(scopes, that.scopes) && Objects.equals(clientSettings, that.clientSettings)
                && Objects.equals(tokenSettings, that.tokenSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, clientIdIssuedAt, clientSecret, clientSecretExpiresAt, clientName,
                clientAuthenticationMethods, authorizationGrantTypes, redirectUris, postLogoutRedirectUris, scopes,
                clientSettings, tokenSettings);
    }

    @Override
    public String toString() {
        return "OAuthClient{" + "id='" + id + '\'' + ", clientId='" + clientId + '\'' + ", clientIdIssuedAt="
                + clientIdIssuedAt + ", clientSecret='" + clientSecret + '\'' + ", clientSecretExpiresAt="
                + clientSecretExpiresAt + ", clientName='" + clientName + '\'' + ", clientAuthenticationMethods="
                + clientAuthenticationMethods + ", authorizationGrantTypes=" + authorizationGrantTypes
                + ", redirectUris=" + redirectUris + ", postLogoutRedirectUris=" + postLogoutRedirectUris + ", scopes="
                + scopes + ", clientSettings='" + clientSettings + '\'' + ", tokenSettings='" + tokenSettings + '\''
                + '}';
    }
}
