package br.com.ismyburguer.controlepedido.adapter.client;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GerarControlePedidoAPIImplTest {

    @Mock
    private FeignClientAPI feignClientAPI;

    @Mock
    private ControlePedidoAPI controlePedidoAPI;

    @InjectMocks
    private GerarControlePedidoAPIImpl gerarControlePedidoAPI;

    @BeforeEach
    void setUp() {
        when(feignClientAPI.createClient(any())).thenReturn(controlePedidoAPI);
        gerarControlePedidoAPI = new GerarControlePedidoAPIImpl(feignClientAPI);
    }

    @Test
    void deveGerarControlePedidoComSucesso() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        UUID controlePedidoId = UUID.randomUUID();
        when(controlePedidoAPI.receberPedido(pedidoId)).thenReturn(controlePedidoId);

        // Act
        UUID resultado = gerarControlePedidoAPI.gerar(new ControlePedido.PedidoId(pedidoId));

        // Assert
        assertNotNull(resultado);
        assertEquals(controlePedidoId, resultado);
    }

    @Test
    void deveLancarConstraintViolationExceptionAoPassarPedidoIdInvalido() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        when(controlePedidoAPI.receberPedido(pedidoId)).thenThrow(FeignException.class);

        // Act & Assert
        assertThrows(FeignException.class, () -> {
            gerarControlePedidoAPI.gerar(new ControlePedido.PedidoId(pedidoId));
        });
    }

    @Test
    void deveLancarFeignExceptionnAoPassarPedidoIdInvalido() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        when(controlePedidoAPI.receberPedido(pedidoId)).thenThrow(FeignException.class);

        // Act & Assert
        assertThrows(FeignException.class, () -> {
            gerarControlePedidoAPI.gerar(new ControlePedido.PedidoId(pedidoId));
        });
    }
}
