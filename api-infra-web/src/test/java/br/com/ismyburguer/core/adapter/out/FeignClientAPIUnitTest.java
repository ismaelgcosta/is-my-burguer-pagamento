package br.com.ismyburguer.core.adapter.out;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FeignClientAPIUnitTest {
    private static SSLContextBuilder contextBuilder;
    private OAuth2ClientCredentialsFeignInterceptorAPI interceptor;
    private FeignClientAPI feignClientAPI;
    @BeforeEach
    void setUp() {
        interceptor = mock(OAuth2ClientCredentialsFeignInterceptorAPI.class);
        feignClientAPI = new FeignClientAPI(interceptor, new ObjectMapper());
        feignClientAPI.setApiGateway("gateway");
    }

    @BeforeAll
    public static void beforeAll() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        mockStatic(Feign.class);

        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                .build();

        mockStatic(SSLContextBuilder.class);
        contextBuilder = mock(SSLContextBuilder.class);
        when(SSLContextBuilder.create()).thenReturn(contextBuilder);
        when(contextBuilder.loadTrustMaterial(TrustAllStrategy.INSTANCE)).thenReturn(contextBuilder);
        when(contextBuilder.build()).thenReturn(sslContext);
    }

    @Test
    void deveCriarClienteFeignCorretamente() {
        // Mocking dependencies
        Feign.Builder builderMock = mock(Feign.Builder.class);
        Feign feignMock = mock(Feign.class);

        when(Feign.builder()).thenReturn(builderMock);

        // Configurando comportamento do Feign.Builder
        when(builderMock.encoder(any())).thenReturn(builderMock);
        when(builderMock.decoder(any())).thenReturn(builderMock);
        when(builderMock.requestInterceptor(interceptor)).thenReturn(builderMock);

        // Configurando comportamento do Feign
        when(builderMock.client(any())).thenReturn(builderMock);

        // Configurando comportamento do Client.Default
        when(builderMock.target(any(), anyString())).thenReturn(feignMock);

        feignClientAPI.createClient(Feign.Builder.class);
    }

    @Test
    void deveCriarClienteFeignCorretamenteComErro() throws NoSuchAlgorithmException, KeyStoreException {
        // Mocking dependencies
        Feign.Builder builderMock = mock(Feign.Builder.class);
        when(Feign.builder()).thenReturn(builderMock);

        // Configurando comportamento do Feign.Builder
        when(builderMock.encoder(any())).thenReturn(builderMock);
        when(builderMock.decoder(any())).thenReturn(builderMock);
        when(builderMock.requestInterceptor(interceptor)).thenReturn(builderMock);

        when(Feign.builder()).thenReturn(builderMock);

        // Configurando comportamento do Feign.Builder
        when(contextBuilder.loadTrustMaterial(TrustAllStrategy.INSTANCE)).thenThrow(new NoSuchAlgorithmException());

        assertThrows(IllegalArgumentException.class, () -> feignClientAPI.createClient(Feign.Builder.class));
    }
}
