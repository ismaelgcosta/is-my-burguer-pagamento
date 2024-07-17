package br.com.ismyburguer.pagamento.web.api.controller;

import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoPorPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.converter.ConsultaPagamentoPedidoConverter;
import br.com.ismyburguer.pagamento.web.api.response.ConsultaPagamentoPedidoResponse;
import br.com.ismyburguer.pagamento.web.api.response.FormaPagamentoResponse;
import br.com.ismyburguer.pagamento.web.api.response.StatusPagamentoResponse;
import br.com.ismyburguer.pagamento.web.api.response.TipoPagamentoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultarPagamentoPedidoAPITest {

    @Mock
    private ConsultarPagamentoPorPedidoUseCase consultarPagamentoPorPedidoUseCase;

    @Mock
    private ConsultaPagamentoPedidoConverter consultaPagamentoPedidoConverter;

    @InjectMocks
    private ConsultarPagamentoPedidoAPI consultarPagamentoPedidoAPI;

    @Test
    void testConsultarPagamentoPedidoAPI() {
        // Criação do objeto de resposta simulada
        ConsultaPagamentoPedidoResponse respostaSimulada = new ConsultaPagamentoPedidoResponse(
                UUID.randomUUID(),
                new StatusPagamentoResponse("PAGO", "PAGO"),
                new FormaPagamentoResponse("MERCADO_PAGO", "MERCADO_PAGO"),
                new TipoPagamentoResponse("QR_CODE", "QR_CODE"),
                "QR_CODE_GERADO"
        );

        // Mockando o comportamento do caso de uso e do conversor
        Pagamento pagamentoSimulado = new Pagamento();
        when(consultarPagamentoPorPedidoUseCase.consultar(anyString())).thenReturn(pagamentoSimulado);
        when(consultaPagamentoPedidoConverter.convert(pagamentoSimulado)).thenReturn(respostaSimulada);

        // Chamando o método da API
        ConsultaPagamentoPedidoResponse resposta = consultarPagamentoPedidoAPI.obter("pedido-id");

        // Verificação do resultado
        assertEquals(respostaSimulada, resposta);
    }
}
