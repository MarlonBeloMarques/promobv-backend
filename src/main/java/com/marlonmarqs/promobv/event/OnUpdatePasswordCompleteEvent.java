package com.marlonmarqs.promobv.event;

import java.util.Locale;

import com.marlonmarqs.promobv.domain.Usuario;

public class OnUpdatePasswordCompleteEvent extends AbstractOnCompleteEvent {
	private static final long serialVersionUID = 1L;
	
	public OnUpdatePasswordCompleteEvent(Usuario user, Locale locale, String appUrl) {
		super(user, locale, appUrl);

	}

}
