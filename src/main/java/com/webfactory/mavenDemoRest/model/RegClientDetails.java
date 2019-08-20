package com.webfactory.mavenDemoRest.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="clientdetails")
public class RegClientDetails {
    @Id
    @Column(name = "clientid", nullable = false)
    @NotNull
    private String clientId;

    @Column(name = "clientsecret")
    @NotNull
    private String clientSecret;

    @Column
    @NotNull
    private String grantType;

    @Column
    @NotNull
    private String scope;


    //constructor
    public RegClientDetails() {
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public RegClientDetails(@NotNull String clientId, @NotNull String clientSecret, @NotNull String scope, @NotNull String grantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.grantType = grantType;
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
