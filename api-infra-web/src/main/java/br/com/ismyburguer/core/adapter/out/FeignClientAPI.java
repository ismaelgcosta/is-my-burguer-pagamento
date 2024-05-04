package br.com.ismyburguer.core.adapter.out;

import feign.Client;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
public class FeignClientAPI {

    @Value("${aws.api-gateway}")
    protected String apiGateway;

    protected final OAuth2ClientCredentialsFeignInterceptorAPI interceptor;

    public FeignClientAPI(OAuth2ClientCredentialsFeignInterceptorAPI interceptor) {
        this.interceptor = interceptor;
    }

    public <T> T createClient(Class<T> apiType) {
        Feign.Builder builder = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder());

        builder.requestInterceptor(interceptor);

        try {
            Client.Default client = new Client.Default(SSLContextBuilder.create()
                    .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                    .build().getSocketFactory(), NoopHostnameVerifier.INSTANCE);
            return builder
                    .client(client)
                    .target(apiType, apiGateway);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

}
