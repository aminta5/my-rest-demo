package com.webfactory.mavenDemoRest.services;

import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.model.VerificationToken;

public interface VerificationTokenService {
    VerificationToken getTokenByUser(User user);

    VerificationToken saveToken(VerificationToken token);

    VerificationToken createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String verificationToken);
}
