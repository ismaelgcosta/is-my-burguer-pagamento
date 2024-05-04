package br.com.ismyburguer.pedido.adapter.client;

import br.com.ismyburguer.pedido.adapter.response.PedidoResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.UUID;

public interface PedidoAPI {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /pedidos/{pedidoId}")
    PedidoResponse findById(@Param("pedidoId") UUID pedidoId);

    @Headers("Content-Type: application/json")
    @RequestLine("PUT /pedidos/{pedidoId}/{status}")
    void alterarStatus(@Param("pedidoId") UUID pedidoId, @Param("status") String status);

}
