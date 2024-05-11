package br.com.ismyburguer.pedido.usecase.impl;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ConsultarPedidoAPI;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsultarPedidoUseCaseImplTest {

    private ConsultarPedidoAPI consultarPedidoAPI;
    private ConsultarPedidoUseCase useCase;

    @BeforeEach
    void setUp() {
        consultarPedidoAPI = mock(ConsultarPedidoAPI.class);
        useCase = new ConsultarPedidoUseCaseImpl(consultarPedidoAPI);
    }

    @Test
    void testBuscarPorId_PedidoEncontrado() {
        // Configuração
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID().toString());
        Pedido pedidoMock = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pedido.class);
        when(consultarPedidoAPI.obterPeloPedidoId(pedidoId.getPedidoId())).thenReturn(Optional.of(pedidoMock));

        // Execução
        Pedido pedidoRetornado = useCase.buscarPorId(pedidoId);

        // Verificação
        assertNotNull(pedidoRetornado);
        assertEquals(pedidoMock, pedidoRetornado);
    }

    @Test
    void testBuscarPorId_PedidoNaoEncontrado() {
        // Configuração
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID().toString());
        when(consultarPedidoAPI.obterPeloPedidoId(pedidoId.getPedidoId())).thenReturn(Optional.empty());

        // Verificação
        assertThrows(EntityNotFoundException.class, () -> useCase.buscarPorId(pedidoId));
    }
}
