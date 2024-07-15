package br.com.ismyburguer.controlepedido.usecase.impl.impl;


import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoAPI;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GerarControlePedidoUseCaseImplTest {

    @Mock
    private GerarControlePedidoAPI gerarControlePedidoAPI;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @InjectMocks
    private GerarControlePedidoUseCaseImpl gerarControlePedidoUseCase;

    @Test
    void deveRetornarIdDoControlePedidoAposReceberPedido() {
        // Arrange
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId(UUID.randomUUID());
        UUID controlePedidoId = UUID.randomUUID();
        when(gerarControlePedidoAPI.gerar(pedidoId)).thenReturn(controlePedidoId);

        // Act
        gerarControlePedidoUseCase.receberPedido(pedidoId);

        // Assert
        verify(alterarStatusPedidoUseCase, times(1)).alterar(any(), eq(Pedido.StatusPedido.RECEBIDO));
    }
}
