package com.moses.oauth2.controller;

import com.moses.oauth2.dto.ClientResponse;
import com.moses.oauth2.dto.CreateClientRequest;
import com.moses.oauth2.dto.CreateClientResponse;
import com.moses.oauth2.service.ClientManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth/clients")
@RequiredArgsConstructor
public class ClientManagementController {

    private final ClientManagementService clientManagementService;

    @PostMapping
    public CreateClientResponse createClient(@RequestBody CreateClientRequest request) {
        RegisteredClient client = clientManagementService.createClient(request);

        return new CreateClientResponse(client.getClientId(), client.getClientName(), client.getRedirectUris(),
                client.getScopes());

    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteClient(@PathVariable String clientId) {
        try {
            clientManagementService.deleteClient(clientId);
            return "Client deleted successfully";
        } catch (Exception e) {
            return "Error deleting client: " + e.getMessage();
        }
    }

    @GetMapping("/{clientId}")
    public ClientResponse getClient(@PathVariable String clientId) {
        RegisteredClient client = clientManagementService.findByClientId(clientId);
        if (client != null) {
            return new ClientResponse(client.getClientId(), client.getClientName(), client.getRedirectUris(),
                    client.getScopes());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
    }
}
