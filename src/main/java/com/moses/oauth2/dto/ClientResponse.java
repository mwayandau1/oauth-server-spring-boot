package com.moses.oauth2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private String clientId;
    private String clientName;
    private Set<String> redirectUris;
    private Set<String> scopes;

}
