package com.webfactory.mavenDemoRest.daoServices;

import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.model.VerificationToken;

public interface VerificationTokenDaoService {
    VerificationToken findVerificationTokenByTokenString(String token);

    VerificationToken findTokenByUser(User user);

    void saveToken(VerificationToken token);

    void createVerificationToken(User user, String token);

    public VerificationToken getVerificationToken(String verificationToken);
}
