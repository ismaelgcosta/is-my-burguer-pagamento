package br.com.ismyburguer.controlepedido.entity;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import jakarta.validation.ConstraintViolationException;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ControlePedidoTest {

    @Test
    void deveRespeitarAsRegrasMinimas() {
        EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(ControlePedido.class);
        GetterSetterVerifier.forClass(ControlePedido.class).verify();
        EqualsVerifier.forClass(ControlePedido.class).suppress(
                Warning.STRICT_INHERITANCE,
                Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                Warning.ALL_FIELDS_SHOULD_BE_USED,
                Warning.BIGDECIMAL_EQUALITY,
                Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void deveCriarControlePedidoComSucesso() {
        // Preparação
        UUID pedidoId = UUID.randomUUID();

        // Ação
        ControlePedido controlePedido = new ControlePedido(
                new ControlePedido.ControlePedidoId(UUID.randomUUID()),
                new ControlePedido.PedidoId(pedidoId.toString()),
                ControlePedido.StatusControlePedido.EM_PREPARACAO,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Verificação
        assertNotNull(controlePedido);
        assertEquals(pedidoId.toString(), controlePedido.getPedidoId().getPedidoId().toString());
        assertEquals(ControlePedido.StatusControlePedido.EM_PREPARACAO.getDescricao(), controlePedido.getStatusControlePedido().getDescricao());
        assertNotNull(controlePedido.getRecebidoEm());
        assertNotNull(controlePedido.getInicioDaPreparacao());
        assertNotNull(controlePedido.getFimDaPreparacao());
        assertNotNull(controlePedido.getControlePedidoId());
    }

    @Test
    void deveLancarBusinessExceptionAoTentarAtualizarParaProntoSemEstarEmPreparacao() {
        // Preparação
        ControlePedido controlePedido = new ControlePedido(null);

        // Verificação
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, controlePedido::validate);
        assertEquals("pedidoId: Informe o código do Pedido", exception.getMessage());
    }
}