package br.com.ismyburguer.pedido.adapter.client;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapter.converter.PedidoResponseToPedidoConverter;
import br.com.ismyburguer.pedido.adapter.response.PedidoResponse;
import br.com.ismyburguer.pedido.entity.Pedido;
import feign.FeignException;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultarPedidoAPIImplTest {

    @Mock
    private FeignClientAPI feignClientAPI;

    @Mock
    private PedidoAPI pedidoAPI;

    @Mock
    private PedidoResponseToPedidoConverter converter;

    private ConsultarPedidoAPIImpl consultarPedidoAPI;

    @BeforeEach
    void setUp() {
        when(feignClientAPI.createClient(PedidoAPI.class)).thenReturn(pedidoAPI);

        consultarPedidoAPI = new ConsultarPedidoAPIImpl(
                feignClientAPI,
                converter
        );
    }

    @Test
    void obterPeloPedidoId_DeveRetornarPedido_QuandoEncontrado() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        Pedido pedidoMock = mock(Pedido.class);
        PedidoResponse pedidoResponseMock = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(PedidoResponse.class);

        // Configurações do mock
        when(pedidoAPI.findById(pedidoId)).thenReturn(pedidoResponseMock);
        when(converter.convert(pedidoResponseMock)).thenReturn(pedidoMock);

        // Act
        Optional<Pedido> resultado = consultarPedidoAPI.obterPeloPedidoId(pedidoId);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(pedidoMock, resultado.get());

        // Verificações
        verify(feignClientAPI, times(1)).createClient(PedidoAPI.class);
        verify(pedidoAPI, times(1)).findById(pedidoId);
        verify(converter, times(1)).convert(pedidoResponseMock);
    }

    @Test
    void obterPeloPedidoId_DeveLancarEntityNotFoundException_QuandoNaoEncontrado() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();

        // Configurações do mock
        when(pedidoAPI.findById(pedidoId)).thenThrow(FeignException.class);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> consultarPedidoAPI.obterPeloPedidoId(pedidoId));

        // Verificações
        verify(feignClientAPI, times(1)).createClient(PedidoAPI.class);
        verify(pedidoAPI, times(1)).findById(pedidoId);
        verify(converter, never()).convert(any());
    }
}
