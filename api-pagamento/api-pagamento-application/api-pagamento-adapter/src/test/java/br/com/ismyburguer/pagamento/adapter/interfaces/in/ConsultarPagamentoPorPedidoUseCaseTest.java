package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsultarPagamentoPorPedidoUseCaseTest {

    @Mock
    private ConsultarPagamentoPorPedidoUseCase consultarPagamentoPorPedidoUseCase;

    @Test
    void deveChamarMetodoConsultarComString() {
        String pedidoId = UUID.randomUUID().toString();

        consultarPagamentoPorPedidoUseCase.consultar(UUID.fromString(pedidoId));

        verify(consultarPagamentoPorPedidoUseCase).consultar(UUID.fromString(pedidoId));
    }
}
