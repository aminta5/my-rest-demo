package com.webfactory.mavenDemoRest.events;

import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.daoServices.VerificationTokenDaoService;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.UUID;

public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
    private final UserDaoService userService;

    private final MessageSource messages;

    private final MailSender mailSender;

    private final VerificationTokenDaoService tokenDaoService;

    public RegistrationEmailListener(UserDaoService userService, MessageSource messages, MailSender mailSender, VerificationTokenDaoService tokenDaoService) {
        this.userService = userService;
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
        tokenDaoService.createVerificationToken(user,token);

        String recipient = user.getEmail();
        String subject = "Registration Confirmation";
        String url
                = event.getAppUrl() + "/confirmRegistration?token=" + token;
        String message = messages.getMessage("message.registrationSuccessConfirmationLink", null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(message + "http://localhost:8080" + url);
        System.out.println(url);
        mailSender.send(email);

    }
}
