package br.com.ismyburguer.pagamento.web.api.converter;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.FormaPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.TipoPagamento;
import br.com.ismyburguer.pagamento.web.api.request.PagamentoRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagamentoToPagamentoRequestConverterTest {

    private PagamentoToPagamentoRequestConverter converter;

    @BeforeEach
    void setUp() {
        converter = new PagamentoToPagamentoRequestConverter();
    }

    @Test
    void testeConversaoSucesso() {
        // Configuração
        UUID pedidoId = UUID.randomUUID();
        Pagamento pagamento = new Pagamento(
                new Pagamento.PagamentoId(UUID.randomUUID()),
                new Pagamento.PedidoId(pedidoId),
                new Pagamento.Total(BigDecimal.ONE),
                Pagamento.StatusPagamento.PAGO,
                Pagamento.FormaPagamento.MERCADO_PAGO,
                Pagamento.TipoPagamento.QR_CODE,
                "qr-code"
        );

        // Execução
        PagamentoRequest request = converter.convert(pagamento);

        // Verificação
        assertNotNull(request);
        assertEquals(pedidoId, request.getPedidoId());
        assertEquals(StatusPagamento.PAGO, request.getStatusPagamento());
        assertEquals(TipoPagamento.QR_CODE, request.getTipoPagamento());
        assertEquals(FormaPagamento.MERCADO_PAGO, request.getFormaPagamento());
        assertEquals(BigDecimal.ONE, request.getValorTotal());
        assertEquals("qr-code", request.getQrCode());
    }

}
