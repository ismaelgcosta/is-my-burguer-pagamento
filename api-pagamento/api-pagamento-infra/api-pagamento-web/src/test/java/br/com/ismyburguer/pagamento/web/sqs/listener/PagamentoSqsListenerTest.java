package br.com.ismyburguer.pagamento.web.sqs.listener;

import br.com.ismyburguer.pagamento.adapter.interfaces.in.EstornarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.GerarPagamentoPedidoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.converter.PagamentoRequestToPagamentoConverter;
import br.com.ismyburguer.pagamento.web.api.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.web.api.request.PagamentoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoSqsListenerTest {

    @Mock
    private GerarPagamentoPedidoUseCase gerarPagamentoPedidoUseCase;
    @Mock
    private EstornarPagamentoPedidoUseCase estornarPagamentoPedidoUseCase;
    @Mock
    private PagamentoRequestToPagamentoConverter pagamentoRequestToPagamentoConverter;
    @Mock
    private ObjectMapper objectMapper;

    private PagamentoSqsListener pagamentoSqsListener;

    @BeforeEach
    void setUp() {
        pagamentoSqsListener = new PagamentoSqsListener(gerarPagamentoPedidoUseCase, estornarPagamentoPedidoUseCase,
                pagamentoRequestToPagamentoConverter, objectMapper);
    }

    @Test
    void deveChamarGerarPagamentoParaPagamentoNovo() throws JsonProcessingException {
        UUID pedidoId = UUID.randomUUID();
        String message = "{" +
                "\"pedidoId\": \"" + pedidoId + "\"," +
                "\"valor\": 100.00," +
                "\"statusPagamento\": \"PAGAMENTO_REALIZADO\"" +
                "}";
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        pagamentoRequest.setPedidoId(pedidoId);
        pagamentoRequest.setValorTotal(BigDecimal.valueOf(100.00));
        pagamentoRequest.setStatusPagamento(StatusPagamento.PAGO);
        Pagamento pagamentoMock = mock(Pagamento.class);

        when(objectMapper.readValue(message, PagamentoRequest.class)).thenReturn(pagamentoRequest);
        when(pagamentoRequestToPagamentoConverter.convert(pagamentoRequest)).thenReturn(pagamentoMock);

        pagamentoSqsListener.gerarPagamento(message);

        verify(objectMapper).readValue(message, PagamentoRequest.class);
        verify(pagamentoRequestToPagamentoConverter).convert(pagamentoRequest);
        verify(gerarPagamentoPedidoUseCase).iniciarPagamento(pagamentoMock);
        verify(estornarPagamentoPedidoUseCase, never()).estornarPagamento(any());
    }

    @Test
    void deveChamarGerarPagamentoParaPagamentoNovoEstornado() throws JsonProcessingException {
        UUID pedidoId = UUID.randomUUID();
        String message = "{" +
                "\"pedidoId\": \"" + pedidoId + "\"," +
                "\"valor\": 100.00," +
                "\"statusPagamento\": \"PAGAMENTO_REALIZADO\"" +
                "}";
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        pagamentoRequest.setPedidoId(pedidoId);
        pagamentoRequest.setValorTotal(BigDecimal.valueOf(100.00));
        pagamentoRequest.setStatusPagamento(StatusPagamento.ESTORNADO);
        Pagamento pagamentoMock = mock(Pagamento.class);

        when(objectMapper.readValue(message, PagamentoRequest.class)).thenReturn(pagamentoRequest);

        pagamentoSqsListener.gerarPagamento(message);

        verify(objectMapper).readValue(message, PagamentoRequest.class);
        verify(gerarPagamentoPedidoUseCase, never()).iniciarPagamento(pagamentoMock);
        verify(estornarPagamentoPedidoUseCase).estornarPagamento(any());
    }

    @Test
    void deveChamarGerarPagamentoParaPagamentoException() throws JsonProcessingException {
        UUID pedidoId = UUID.randomUUID();
        String message = "{" +
                "\"pedidoId\": \"" + pedidoId + "\"," +
                "\"valor\": 100.00," +
                "\"statusPagamento\": \"PAGAMENTO_REALIZADO\"" +
                "}";
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        pagamentoRequest.setPedidoId(pedidoId);
        pagamentoRequest.setValorTotal(BigDecimal.valueOf(100.00));
        pagamentoRequest.setStatusPagamento(StatusPagamento.ESTORNADO);
        Pagamento pagamentoMock = mock(Pagamento.class);

        when(objectMapper.readValue(message, PagamentoRequest.class)).thenThrow(new RuntimeException("asas"));

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> pagamentoSqsListener.gerarPagamento(message));

        assertNotNull(illegalArgumentException);
    }

}
