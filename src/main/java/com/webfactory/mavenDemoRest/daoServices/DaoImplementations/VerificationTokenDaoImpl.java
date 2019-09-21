package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.daoServices.VerificationTokenDaoService;
import com.webfactory.mavenDemoRest.exceptions.TokenNotFoundException;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.model.VerificationToken;
import com.webfactory.mavenDemoRest.repositories.VerificationTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenDaoImpl implements VerificationTokenDaoService {
    private final VerificationTokenRepository tokenRepository;

    public VerificationTokenDaoImpl(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public VerificationToken findTokenByUser(User user) {
        return tokenRepository.findByUser(user).orElseThrow(TokenNotFoundException::new);
    }

    @Override
    public VerificationToken saveToken(VerificationToken token) {
        return tokenRepository.save(token);
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken newUserToken = new VerificationToken(token, user);
        return tokenRepository.save(newUserToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken).orElseThrow(TokenNotFoundException::new);
    }
}
