package br.com.ismyburguer.pedido.adapter.request;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoRequestTest {

    @Test
    void run() {
        new PedidoRequest();
        UUID pedidoId = UUID.randomUUID();
        PedidoRequest pedidoRequest = new PedidoRequest(pedidoId, StatusPedidoRequest.RECEBIDO);
        assertEquals(pedidoRequest.getPedidoId(), pedidoId);
        assertEquals(pedidoRequest.getStatusPedido(), StatusPedidoRequest.RECEBIDO);

        PedidoRequest pedidoRequest1 = new PedidoRequest(pedidoId, StatusPedidoRequest.RECEBIDO);

        assertTrue(pedidoRequest.equals(pedidoRequest1));
    }
}