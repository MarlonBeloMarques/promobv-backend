package com.marlonmarqs.promobv.security.oauth2;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.domain.enums.AuthProvider;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.security.UserSS;
import com.marlonmarqs.promobv.security.exceptions.OAuth2AuthenticationProcessingException;
import com.marlonmarqs.promobv.security.oauth2.user.OAuth2UserInfo;
import com.marlonmarqs.promobv.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Lançar uma instância de AuthenticationException irá acionar o OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email não encontrado no provedor OAuth2");
        }

        Usuario usuario = repository.findByEmail(oAuth2UserInfo.getEmail());
        if(usuario != null) {
            if(!usuario.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Parece que você se inscreveu com " + usuario.getProvider() +
                        " conta. Por favor, use o seu " + usuario.getProvider() + " conta para fazer o login.");
            }

            usuario = updateExistingUser(usuario, oAuth2UserInfo);
        } else {
            usuario = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return (OAuth2User) new UserSS(usuario.getId(), usuario.getEmail(), usuario.getSenha(), usuario.getAtivado(), usuario.getPerfis());
    }

    private Usuario registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Usuario usuario = new Usuario();

        usuario.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        usuario.setProviderId(oAuth2UserInfo.getId());
        usuario.setNome(oAuth2UserInfo.getName());
        usuario.setEmail(oAuth2UserInfo.getEmail());
        usuario.setUrlProfile(oAuth2UserInfo.getImageUrl());

        return repository.save(usuario);
    }

    private Usuario updateExistingUser(Usuario existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setNome(oAuth2UserInfo.getName());
        existingUser.setUrlProfile(oAuth2UserInfo.getImageUrl());
        return repository.save(existingUser);
    }
}
