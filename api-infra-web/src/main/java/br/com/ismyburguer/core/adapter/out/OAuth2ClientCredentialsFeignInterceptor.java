package br.com.ismyburguer.core.adapter.out;

import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class OAuth2ClientCredentialsFeignInterceptor implements OAuth2ClientCredentialsFeignInterceptorAPI {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TOKEN = "Bearer {0}";

    public void apply(RequestTemplate template) {
        template.header(AUTHORIZATION, MessageFormat.format(BEARER_TOKEN, ((Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials()).getTokenValue()));
    }
}