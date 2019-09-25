package com.webfactory.mavenDemoRest.services.serviceImplementations;

import com.webfactory.mavenDemoRest.exceptions.TokenNotFoundException;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.model.VerificationToken;
import com.webfactory.mavenDemoRest.repositories.VerificationTokenRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class VerificationTokenDaoImplTest {
    private VerificationTokenServiceImpl verificationService;

    @Mock
    VerificationTokenRepository tokenRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        verificationService = new VerificationTokenServiceImpl(tokenRepository);
    }

    @Test
    public void findTokenByUser() {
        VerificationToken token = new VerificationToken();
        Optional<VerificationToken> tokenOptional = Optional.of(token);
        when(tokenRepository.findByUser(any())).thenReturn(tokenOptional);
        VerificationToken foundToken = verificationService.getTokenByUser(any());
        assertNotNull(foundToken, "Token by User not found");
        verify(tokenRepository, times(1)).findByUser(any());
    }

    @Test(expected = TokenNotFoundException.class)
    public void throwTokenNotFoundException(){
        when(tokenRepository.findByUser(any())).thenReturn(Optional.empty());
        verificationService.getTokenByUser(any());

    }

    @Test
    public void saveToken() {
        VerificationToken token = new VerificationToken();
        when(tokenRepository.save(any())).thenReturn(token);
        VerificationToken savedToken = verificationService.saveToken(token);
        assertNotNull(savedToken, "Token not saved");
        verify(tokenRepository, times(1)).save(any());
    }

    @Test
    public void createVerificationToken() {
        User user = User.builder().build();
        VerificationToken token = new VerificationToken("token", user);
        when(tokenRepository.save(any())).thenReturn(token);
        VerificationToken savedToken = verificationService.createVerificationToken(user, "token");
        assertNotNull(savedToken, "Token not created");
        verify(tokenRepository, times(1)).save(any());
    }

    @Test
    public void getVerificationToken() {
        VerificationToken token = new VerificationToken();
        token.setToken("token");
        Optional<VerificationToken> optionalToken = Optional.of(token);
        when(tokenRepository.findByToken(anyString())).thenReturn(optionalToken);
        VerificationToken foundToken = verificationService.getVerificationToken(anyString());
        assertNotNull(foundToken, "Token not found");
        verify(tokenRepository, times(1)).findByToken(anyString());
    }

    @Test(expected = TokenNotFoundException.class)
    public void throwExceptionIfVerificationTokenNotFound(){
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());
        verificationService.getVerificationToken(anyString());
    }
}