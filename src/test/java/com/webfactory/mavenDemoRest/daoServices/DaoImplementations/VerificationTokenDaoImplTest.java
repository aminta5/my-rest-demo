package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.model.VerificationToken;
import com.webfactory.mavenDemoRest.repositories.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VerificationTokenDaoImplTest {

    private VerificationTokenDaoImpl verificationService;

    @Mock
    VerificationTokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        verificationService = new VerificationTokenDaoImpl(tokenRepository);
    }

    @Test
    void findTokenByUser() {
        VerificationToken token = new VerificationToken();
        Optional<VerificationToken> tokenOptional = Optional.of(token);
        when(tokenRepository.findByUser(any())).thenReturn(tokenOptional);
        VerificationToken foundToken = verificationService.findTokenByUser(any());
        assertNotNull(foundToken, "Token by User not found");
        verify(tokenRepository, times(1)).findByUser(any());
    }

    @Test
    void saveToken() {
        VerificationToken token = new VerificationToken();
        when(tokenRepository.save(any())).thenReturn(token);
        VerificationToken savedToken = verificationService.saveToken(token);
        assertNotNull(savedToken, "Token not saved");
        verify(tokenRepository, times(1)).save(any());
    }

    @Test
    void createVerificationToken() {
        User user = User.builder().build();
        VerificationToken token = new VerificationToken("token", user);
        when(tokenRepository.save(any())).thenReturn(token);
        VerificationToken savedToken = verificationService.createVerificationToken(user, "token");
        assertNotNull(savedToken, "Token not created");
        verify(tokenRepository, times(1)).save(any());
    }

    @Test
    void getVerificationToken() {
        VerificationToken token = new VerificationToken();
        token.setToken("token");
        Optional<VerificationToken> optionalToken = Optional.of(token);
        when(tokenRepository.findByToken(anyString())).thenReturn(optionalToken);
        VerificationToken foundToken = verificationService.getVerificationToken(anyString());
        assertNotNull(foundToken, "Token not found");
        verify(tokenRepository, times(1)).findByToken(anyString());
    }
}