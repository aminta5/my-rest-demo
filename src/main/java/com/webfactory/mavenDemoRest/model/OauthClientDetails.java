package com.webfactory.mavenDemoRest.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="oauth_client_details")
public class OauthClientDetails {
    @Id
    @Column(name = "clientid", nullable = false)
    @NotNull
    private String clientId;

    @Column(name = "clientsecret")
    @NotNull
    private String clientSecret;

    @Column
    @NotNull
    private String authorizedGrantType;

    @Column
    @NotNull
    private String scope;


    //constructor
    public OauthClientDetails() {
    }

    public String getAuthorizedGrantType() {
        return authorizedGrantType;
    }

    public void setAuthorizedGrantType(String authorizedGrantType) {
        this.authorizedGrantType = authorizedGrantType;
    }

    public OauthClientDetails(@NotNull String clientId, @NotNull String clientSecret, @NotNull String scope, @NotNull String authorizedGrantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.authorizedGrantType = authorizedGrantType;
    }

    //getters and setters


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
