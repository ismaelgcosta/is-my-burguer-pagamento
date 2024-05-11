package br.com.ismyburguer.pedido.adapter.client;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.entity.Pedido;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarStatusPedidoAPIImplTest {

    @Mock
    private FeignClientAPI feignClientAPI;

    @Mock
    private PedidoAPI pedidoAPI;

    private AlterarStatusPedidoAPIImpl alterarStatusPedidoAPI;

    @BeforeEach
    void setUp() {
        when(feignClientAPI.createClient(PedidoAPI.class)).thenReturn(pedidoAPI);

        alterarStatusPedidoAPI = new AlterarStatusPedidoAPIImpl(
                feignClientAPI
        );
    }
    @Test
    void alterar_DeveChamarAPI_QuandoPedidoExiste() {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido statusPedido = Pedido.StatusPedido.FECHADO;

        // Act
        alterarStatusPedidoAPI.alterar(pedidoId, statusPedido);

        // Verificações
        verify(feignClientAPI, times(1)).createClient(PedidoAPI.class);
        verify(pedidoAPI, times(1)).alterarStatus(pedidoId.getPedidoId(), statusPedido.name());
    }

    @Test
    void alterar_DeveLancarEntityNotFoundException_QuandoPedidoNaoExiste() {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido statusPedido = Pedido.StatusPedido.FECHADO;

        // Configurações do mock
        doThrow(FeignException.NotFound.class).when(pedidoAPI).alterarStatus(pedidoId.getPedidoId(), statusPedido.name());

        // Act & Assert
        assertThrows(FeignException.NotFound.class, () -> alterarStatusPedidoAPI.alterar(pedidoId, statusPedido));

        // Verificações
        verify(feignClientAPI, times(1)).createClient(PedidoAPI.class);
        verify(pedidoAPI, times(1)).alterarStatus(pedidoId.getPedidoId(), statusPedido.name());
    }
}
