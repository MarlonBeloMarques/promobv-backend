package com.marlonmarqs.promobv.security.oauth2;

import com.marlonmarqs.promobv.security.BadRequestException;
import com.marlonmarqs.promobv.security.JWTUtil;
import com.marlonmarqs.promobv.security.OAuth2Util;
import com.marlonmarqs.promobv.security.UserSS;
import com.marlonmarqs.promobv.security.utils.CookieUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.marlonmarqs.promobv.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private OAuth2Util oAuth2Util;

    private JWTUtil jwtUtil;

    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    public OAuth2AuthenticationSuccessHandler(HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository, OAuth2Util oAuth2Util, JWTUtil jwtUtil) {
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.oAuth2Util = oAuth2Util;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if(response.isCommitted()) {
            logger.debug("A resposta já foi confirmada. Incapaz de redirecionar para " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Desculpa! Temos um URI de redirecionamento não autorizado e não podemos prosseguir com a autenticação");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        UserSS userSS = (UserSS) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userSS.getUsername());

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", "Bearer " + token)
                .build().toString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return oAuth2Util.getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);

                    if(authorizedURI.toString().contains("http")) {
                        // Valide apenas host e porta. Deixe os clientes usarem caminhos diferentes se quiserem
                        if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                                && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                            return true;
                        }
                    } else if(authorizedURI.toString().contains("promobv") || authorizedURI.toString().contains("exp")) {
                        return true;
                    }

                    return false;
                });
    }
}
