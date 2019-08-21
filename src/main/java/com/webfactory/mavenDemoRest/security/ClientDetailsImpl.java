package com.webfactory.mavenDemoRest.security;

import com.webfactory.mavenDemoRest.model.OauthClientDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class ClientDetailsImpl implements ClientDetails {
    private String clientId;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;

    public ClientDetailsImpl(OauthClientDetails oauthClientDetails) {
        this.clientId = oauthClientDetails.getClientId();
        this.clientSecret = oauthClientDetails.getClientSecret();
        this.scope = oauthClientDetails.getScope();
        this.authorizedGrantTypes = oauthClientDetails.getAuthorizedGrantType();
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        Set<String> scopes = new HashSet<>(Arrays.asList(this.scope.split(",")));
        return scopes;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> grantTypes = new HashSet<>(Arrays.asList(this.authorizedGrantTypes.split(",")));
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return null;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return null;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
