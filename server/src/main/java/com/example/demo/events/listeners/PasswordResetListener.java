package com.example.demo.events.listeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.demo.events.Event;
import com.example.demo.rest.models.User;
import com.example.demo.rest.services.AuthService;

@Component
public class PasswordResetListener implements ApplicationListener<Event> {

	@Autowired
    private AuthService authService;
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;
	
	@Override
	public void onApplicationEvent(Event event) {
		this.confirmReset(event);
	}
	
	private void confirmReset(Event event) {
        var user = event.getUser();
        var token = UUID.randomUUID().toString();
        authService.createPasswordResetTokenForUser(user, token);
        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    //

    private SimpleMailMessage constructEmailMessage(Event event, final User user, final String token) {
        var recipientAddress = user.getEmail();
        var subject = "Password Reset";
        var confirmationUrl = "http://localhost:4200" + "/password_reset/" + token;
        var message = messages.getMessage("message.regSuccLink", null, "To update your password please click on the link below", event.getLocale());
        var email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
}

