package br.com.ismyburguer.pedido.adapter.client;

import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.pedido.adapter.request.PedidoRequest;
import br.com.ismyburguer.pedido.adapter.request.StatusPedidoRequest;
import br.com.ismyburguer.pedido.entity.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AlterarStatusPedidoAPIImplTest {

    @Mock
    private FeignClientAPI feignClientAPI;

    @Mock
    private SqsTemplate sqsTemplate;

    private AlterarStatusPedidoAPIImpl alterarStatusPedidoAPI;

    @BeforeEach
    void setUp() {
        alterarStatusPedidoAPI = new AlterarStatusPedidoAPIImpl(sqsTemplate, new ObjectMapper());
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
        verify(sqsTemplate, times(1)).send(new PedidoRequest(pedidoId.getPedidoId(), StatusPedidoRequest.valueOf(statusPedido.name())));
    }

}
