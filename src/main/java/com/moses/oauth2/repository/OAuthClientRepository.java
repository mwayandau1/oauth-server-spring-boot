package com.moses.oauth2.repository;

import com.moses.oauth2.model.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OAuthClientRepository extends JpaRepository<OAuthClient, String> {
    Optional<OAuthClient> findByClientId(String clientId);
}
