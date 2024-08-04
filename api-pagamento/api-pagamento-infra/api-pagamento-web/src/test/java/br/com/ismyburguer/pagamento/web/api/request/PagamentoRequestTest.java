package br.com.ismyburguer.pagamento.web.api.request;

import static org.junit.jupiter.api.Assertions.*;
import br.com.ismyburguer.pagamento.web.api.enumeration.FormaPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.web.api.enumeration.TipoPagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PagamentoRequestTest {

    @Test
    void testDefaultConstructorAndGetter() {
        PagamentoRequest request = new PagamentoRequest();

        // Assert that default values are set correctly
        assertNotNull(request.getPagamentoId());
        assertNull(request.getPedidoId());
        assertNull(request.getStatusPagamento());
        assertNull(request.getTipoPagamento());
        assertNull(request.getFormaPagamento());
        assertNull(request.getValorTotal());
        assertNull(request.getQrCode());
    }

    @Test
    void testAllArgsConstructorAndGetter() {
        UUID pedidoId = UUID.randomUUID();
        StatusPagamento statusPagamento = StatusPagamento.PAGO;
        TipoPagamento tipoPagamento = TipoPagamento.QR_CODE;
        FormaPagamento formaPagamento = FormaPagamento.MERCADO_PAGO;
        BigDecimal valorTotal = new BigDecimal(100.50);
        String qrCode = "https://pagseguro.com.br/qr-code";

        PagamentoRequest request = new PagamentoRequest(pedidoId, statusPagamento, tipoPagamento, formaPagamento, valorTotal, qrCode);

        assertEquals(pedidoId, request.getPedidoId());
        assertEquals(statusPagamento, request.getStatusPagamento());
        assertEquals(tipoPagamento, request.getTipoPagamento());
        assertEquals(formaPagamento, request.getFormaPagamento());
        assertEquals(valorTotal, request.getValorTotal());
        assertEquals(qrCode, request.getQrCode());
    }

    @Test
    void testEqualsAndHashCode() {
        UUID pedidoId = UUID.randomUUID();
        UUID pagamentoId = UUID.randomUUID();
        StatusPagamento statusPagamento = StatusPagamento.PAGO;
        TipoPagamento tipoPagamento = TipoPagamento.QR_CODE;
        FormaPagamento formaPagamento = FormaPagamento.MERCADO_PAGO;
        BigDecimal valorTotal = new BigDecimal("100.50");
        String qrCode = "https://pagseguro.com.br/qr-code";

        PagamentoRequest request1 = new PagamentoRequest(pagamentoId, pedidoId, statusPagamento, tipoPagamento, formaPagamento, valorTotal, qrCode);
        PagamentoRequest request2 = new PagamentoRequest(pagamentoId, pedidoId, statusPagamento, tipoPagamento, formaPagamento, valorTotal, qrCode);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }
}
