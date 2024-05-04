package br.com.ismyburguer;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaNamespace;
import com.netflix.discovery.shared.transport.EurekaHttpClient;
import com.netflix.discovery.shared.transport.decorator.RedirectingEurekaHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.configuration.SSLContextFactory;
import org.springframework.cloud.configuration.TlsProperties;
import org.springframework.cloud.netflix.eureka.http.DefaultEurekaClientHttpRequestFactorySupplier;
import org.springframework.cloud.netflix.eureka.http.EurekaClientHttpRequestFactorySupplier;
import org.springframework.cloud.netflix.eureka.http.RestTemplateDiscoveryClientOptionalArgs;
import org.springframework.cloud.netflix.eureka.http.RestTemplateEurekaHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RestTemplateConfig {

    @Value("eureka.client.serviceUrl.defaultZone")
    private String serviceUrl;

    @Bean
    public EurekaHttpClient eurekaHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return new RestTemplateEurekaHttpClient(restTemplate(), serviceUrl);
    }

    @Bean
    public RestTemplateDiscoveryClientOptionalArgs restTemplateDiscoveryClientOptionalArgs(TlsProperties tlsProperties,
                                                                                           EurekaClientHttpRequestFactorySupplier eurekaClientHttpRequestFactorySupplier)
            throws GeneralSecurityException, IOException {
        RestTemplateDiscoveryClientOptionalArgs args = new RestTemplateDiscoveryClientOptionalArgs(
                eurekaClientHttpRequestFactorySupplier);
        SSLContextFactory factory = new SSLContextFactory(tlsProperties);
        args.setSSLContext(factory.createSSLContext());
        args.setHostnameVerifier(NoopHostnameVerifier.INSTANCE);
        return args;
    }

    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                        .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                                .setSslContext(SSLContextBuilder.create()
                                        .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                                        .build())
                                .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                                .build())
                        .build())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpclient);
        return new RestTemplate(requestFactory);
    }
}