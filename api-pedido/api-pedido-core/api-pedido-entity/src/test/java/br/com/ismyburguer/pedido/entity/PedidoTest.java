package br.com.ismyburguer.pedido.entity;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PedidoTest {

    @Test
    void deveRespeitarAsRegrasMinimas() {
        EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pedido.class);
        GetterSetterVerifier.forClass(Pedido.class).verify();
        EqualsVerifier.forClass(Pedido.class).suppress(
                Warning.STRICT_INHERITANCE,
                Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                Warning.ALL_FIELDS_SHOULD_BE_USED,
                Warning.BIGDECIMAL_EQUALITY,
                Warning.NONFINAL_FIELDS).verify();

        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID().toString());

        Pedido.ClienteId clienteId = new Pedido.ClienteId(UUID.randomUUID().toString());
        Pedido pedido = new Pedido(
                pedidoId,
                clienteId,
                Pedido.StatusPedido.FECHADO,
                BigDecimal.ONE);
        assertEquals(clienteId, pedido.getClienteId().get());
        assertEquals(Pedido.StatusPedido.FECHADO, pedido.getStatusPedido());

    }

}