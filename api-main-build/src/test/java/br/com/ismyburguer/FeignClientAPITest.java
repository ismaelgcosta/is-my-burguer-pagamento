package br.com.ismyburguer;

import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import feign.Headers;
import feign.RequestLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import wiremock.com.fasterxml.jackson.databind.node.TextNode;

import java.util.List;

import static br.com.ismyburguer.TestSecurityConfig.jwt;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(classes = {Application.class, TestSecurityConfig.class})
@AutoConfigureWireMock
public class FeignClientAPITest {

    @Autowired
    private FeignClientAPI feignClientAPI;

    @Test
    void deveConsumirOServico() {
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(
                jwt(), List.of()
        ));
        // Stubbing WireMock
        stubFor(get(urlEqualTo("/dummy")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json").withJsonBody(new TextNode("Hello World!"))));

        FakeAPI client = feignClientAPI.createClient(FakeAPI.class);
        assertEquals("Hello World!", client.dummyCall());
    }

    public interface FakeAPI {

        @Headers("Content-Type: application/json")
        @RequestLine("GET /dummy")
        Object dummyCall();

    }
}
