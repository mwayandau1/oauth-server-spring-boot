package com.moses.oauth2.service;

import com.moses.oauth2.dto.CreateClientRequest;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface ClientManagementService {
    RegisteredClient createClient(CreateClientRequest request);

    void deleteClient(String clientId);

    RegisteredClient findByClientId(String clientId);
}
