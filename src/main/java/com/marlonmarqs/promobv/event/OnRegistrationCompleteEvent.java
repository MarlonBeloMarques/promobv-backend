package com.marlonmarqs.promobv.event;

import java.util.Locale;

import com.marlonmarqs.promobv.domain.Usuario;

public class OnRegistrationCompleteEvent extends AbstractOnCompleteEvent {
	private static final long serialVersionUID = 1L;

	public OnRegistrationCompleteEvent(Usuario user, Locale locale, String appUrl) {
		super(user, locale, appUrl);
		
	}

}
