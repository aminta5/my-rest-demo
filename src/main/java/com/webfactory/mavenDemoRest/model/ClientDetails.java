//package com.webfactory.mavenDemoRest.model;
//
//import com.webfactory.mavenDemoRest.constants.GrantType;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//
//@Entity
//@Table(name="clientDetails")
//public class ClientDetails extends BaseEntity{
//    @Column
//    @NotNull
//    private String secret;
//
//    @Column
//    @NotNull
//    private String scope;
//
//
//    @Enumerated(value = EnumType.STRING)
//    private GrantType grantType;
//
//    public String getSecret() {
//        return secret;
//    }
//
//    public void setSecret(String secret) {
//        this.secret = secret;
//    }
//
//    public String getScope() {
//        return scope;
//    }
//
//    public void setScope(String scope) {
//        this.scope = scope;
//    }
//
//    public GrantType getGrantType() {
//        return grantType;
//    }
//
//    public void setGrantType(GrantType grantType) {
//        this.grantType = grantType;
//    }
//}
