package com.example.event.listeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.event.OnRegistrationCompleteEvent;
import com.example.rest.model.User;
import com.example.service.AuthService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
    private AuthService authService;
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
    private JavaMailSender mailSender;

	@Value("${support.email}")
	private String supportEmail;
	
	@Override
	public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);	
	}
	
	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        var user = event.getUser();
        var token = UUID.randomUUID().toString();
        authService.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    //

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
        var recipientAddress = user.getEmail();
        var subject = "Registration Confirmation";
        var confirmationUrl = "http://localhost:4200" + "/confirmation/" + token;
        var message = messages.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the below link.", event.getLocale());
        var email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(supportEmail);
        return email;
    }

}