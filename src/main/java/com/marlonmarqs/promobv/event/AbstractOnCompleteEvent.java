package com.marlonmarqs.promobv.event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.marlonmarqs.promobv.domain.Usuario;

public abstract class AbstractOnCompleteEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	
	private String appUrl;
    private Locale locale;
    private Usuario user;
 
    public AbstractOnCompleteEvent(Usuario user, Locale locale, String appUrl) {
        super(user);
         
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
     
}