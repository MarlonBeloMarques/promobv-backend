package com.marlonmarqs.promobv.security.oauth2.user;

import com.marlonmarqs.promobv.domain.enums.AuthProvider;
import com.marlonmarqs.promobv.security.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if(registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Desculpe! O acesso com " + registrationId + " ainda não é compatível.");
        }
    }
}
