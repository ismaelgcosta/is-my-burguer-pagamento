package br.com.ismyburguer.core.auth;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwaggerWebConfigurationTest {

    @Test
    void deveRedirecionarParaSwaggerUI() {
        SwaggerWebConfiguration configuration = new SwaggerWebConfiguration();

        // Mock da ViewControllerRegistry
        ViewControllerRegistry registryMock = mock(ViewControllerRegistry.class);

        ViewControllerRegistration controllerRegistration = mock(ViewControllerRegistration.class);
        when(registryMock.addViewController(any())).thenReturn(controllerRegistration);

        // Chamada ao método de adicionar view controller
        configuration.addViewControllers(registryMock);

        // Verifica se o método addViewController foi chamado com a URL correta
        verify(registryMock, times(1)).addViewController("/");
        verify(controllerRegistration, times(1)).setViewName("forward:/swagger-ui/index.html");
    }
}
