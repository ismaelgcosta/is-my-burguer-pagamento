package br.com.ismyburguer.pedido.usecase.impl;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class AlterarStatusPedidoUseCaseImplTest {

    private AlterarStatusPedidoAPI statusPedidoAPI;
    private AlterarStatusPedidoUseCase useCase;

    @BeforeEach
    void setUp() {
        statusPedidoAPI = mock(AlterarStatusPedidoAPI.class);
        useCase = new AlterarStatusPedidoUseCaseImpl(statusPedidoAPI);
    }

    @Test
    void testAlterarStatusPedido() {
        // Configuração
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido novoStatus = Pedido.StatusPedido.FINALIZADO;

        // Execução
        useCase.alterar(pedidoId, novoStatus);

        // Verificação
        verify(statusPedidoAPI, times(1)).alterar(pedidoId, novoStatus);
    }
}
