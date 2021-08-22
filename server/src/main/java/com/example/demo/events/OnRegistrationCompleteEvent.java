package com.example.demo.events;

import java.util.Locale;

import com.example.demo.rest.models.User;

public class OnRegistrationCompleteEvent extends Event {

	private static final long serialVersionUID = 1L;

	public OnRegistrationCompleteEvent(User user, Locale locale) {
		super(user, locale);
	}

}
