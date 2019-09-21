package com.webfactory.mavenDemoRest.daoServices;

import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.model.VerificationToken;

public interface VerificationTokenDaoService {
    VerificationToken findTokenByUser(User user);

    VerificationToken saveToken(VerificationToken token);

    VerificationToken createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String verificationToken);
}
