package br.com.ismyburguer.controlepedido.adapter.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.UUID;

public interface ControlePedidoAPI {

    @Headers("Content-Type: application/json")
    @RequestLine("PUT /controle-pedidos/{pedidoId}/recebimento")
    UUID receberPedido(@Param("pedidoId") UUID pedidoId);
}
