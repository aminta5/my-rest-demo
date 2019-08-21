package com.webfactory.mavenDemoRest.security;

import com.webfactory.mavenDemoRest.model.OauthClientDetails;
import com.webfactory.mavenDemoRest.repositories.ClientDetailsRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    private final ClientDetailsRepository clientDetailsRepository;

    public ClientDetailsServiceImpl(ClientDetailsRepository clientDetailsRepository) {
        this.clientDetailsRepository = clientDetailsRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetails oauthClientDetails = clientDetailsRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        System.out.println(oauthClientDetails);
        return new ClientDetailsImpl(oauthClientDetails);

    }
}
