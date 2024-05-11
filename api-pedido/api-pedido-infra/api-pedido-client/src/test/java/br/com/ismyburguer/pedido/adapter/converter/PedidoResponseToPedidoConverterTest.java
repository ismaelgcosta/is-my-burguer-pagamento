package br.com.ismyburguer.pedido.adapter.converter;
import br.com.ismyburguer.pedido.adapter.response.PedidoResponse;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoResponseToPedidoConverterTest {

    @Test
    void testConvert() {
        // Dados de entrada
        PedidoResponse pedidoResponse = new PedidoResponse();
        UUID pedidoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        pedidoResponse.setPedidoId(pedidoId.toString());
        pedidoResponse.setClienteId(clienteId.toString());
        pedidoResponse.setStatus("FECHADO");
        pedidoResponse.setValorTotal(new BigDecimal("50.00"));

        // Conversão
        PedidoResponseToPedidoConverter converter = new PedidoResponseToPedidoConverter();
        Pedido pedido = converter.convert(pedidoResponse);

        // Verificação
        assertNotNull(pedido);
        assertEquals(pedidoId, pedido.getPedidoId().getPedidoId());
        assertEquals(clienteId, pedido.getClienteId().get().getClienteId());
        assertEquals(Pedido.StatusPedido.FECHADO, pedido.getStatusPedido());
        assertEquals(new BigDecimal("50.00"), pedido.getTotal());
    }

    @Test
    void testConvert_NullClienteId() {
        // Dados de entrada
        PedidoResponse pedidoResponse = new PedidoResponse();
        UUID pedidoId = UUID.randomUUID();
        pedidoResponse.setPedidoId(pedidoId.toString());
        pedidoResponse.setStatus("FECHADO");
        pedidoResponse.setValorTotal(new BigDecimal("50.00"));

        // Conversão
        PedidoResponseToPedidoConverter converter = new PedidoResponseToPedidoConverter();
        Pedido pedido = converter.convert(pedidoResponse);

        // Verificação
        assertNotNull(pedido);
        assertEquals(pedidoId, pedido.getPedidoId().getPedidoId());
        assertEquals(Optional.empty(), pedido.getClienteId());
        assertEquals(Pedido.StatusPedido.FECHADO, pedido.getStatusPedido());
        assertEquals(new BigDecimal("50.00"), pedido.getTotal());
    }
}
