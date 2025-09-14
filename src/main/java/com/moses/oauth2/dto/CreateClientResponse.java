package com.moses.oauth2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CreateClientResponse {
    private String clientId;
    private String clientName;
    private Set<String> redirectUris;
    private Set<String> scopes;

}
