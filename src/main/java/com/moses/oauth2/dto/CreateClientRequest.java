package com.moses.oauth2.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CreateClientRequest {
    private String clientId;
    private String clientSecret;
    private String clientName;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private String postLogoutRedirectUrl;

}
