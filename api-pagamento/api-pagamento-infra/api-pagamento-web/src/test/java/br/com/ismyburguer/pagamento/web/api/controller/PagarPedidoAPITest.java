package br.com.ismyburguer.pagamento.web.api.controller;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.PagarPedidoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagarPedidoAPITest {

    @Mock
    private PagarPedidoUseCase useCase;

    @InjectMocks
    private PagarPedidoAPI api;

    @Test
    void testPagarPedidoAPI_Success() {
        // Configuração do comportamento do caso de uso
        String pedidoId = UUID.randomUUID().toString();
        String qrCode = "qr-code-123";
        when(useCase.pagar(any())).thenReturn(qrCode);

        // Chamada do método da API
        String responseEntity = api.pagar(pedidoId);

        // Verificação do resultado
        assertEquals(qrCode, responseEntity);
    }

}
