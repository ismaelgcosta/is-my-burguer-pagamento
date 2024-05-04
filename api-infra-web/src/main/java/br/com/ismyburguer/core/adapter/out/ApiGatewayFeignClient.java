package br.com.ismyburguer.core.adapter.out;

public interface ApiGatewayFeignClient {

    <T> T createClient(Class<T> apiType);
}
