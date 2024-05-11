package br.com.ismyburguer.core.adapter.out;

import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OAuth2ClientCredentialsFeignInterceptorTest {

    @Mock
    private RequestTemplate template;

    @Mock
    private Jwt jwt;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private OAuth2ClientCredentialsFeignInterceptor interceptor;

    @BeforeEach
    void setUp() {
        mockStatic(SecurityContextHolder.class);
    }

    @Test
    @DisplayName("Deve adicionar token de autorização ao RequestTemplate")
    public void apply_DeveAdicionarTokenDeAutorizacaoAoRequestTemplate() {
        // Arrange
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getCredentials()).thenReturn(jwt);
        when(jwt.getTokenValue()).thenReturn("mockToken");

        // Act
        interceptor.apply(template);

        // Assert
        verify(template).header("Authorization", "Bearer mockToken");
    }
}
