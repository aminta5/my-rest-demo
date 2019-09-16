package com.webfactory.mavenDemoRest.events;

import com.webfactory.mavenDemoRest.daoServices.VerificationTokenDaoService;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

    private final MessageSource messages;

    private final MailSender mailSender;

    private final VerificationTokenDaoService tokenDaoService;


    private Logger logger = Logger.getLogger(getClass().getName());

    public RegistrationEmailListener(@Qualifier("messageSource") MessageSource messages, MailSender mailSender, VerificationTokenDaoService tokenDaoService) {
        this.messages = messages;
        this.mailSender = mailSender;
        this.tokenDaoService = tokenDaoService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenDaoService.createVerificationToken(user, token);

        String recipient = user.getEmail();
        String subject = "Registration Confirmation";
        String url = "'http://localhost:8080/users/new/confirm?token=" + token + "\'";
        String message = messages.getMessage("message.registrationSuccessConfirmationLink", null, Locale.getDefault());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(message + url);
        logger.info("Confirmation link:" + url);
        mailSender.send(email);
    }
}
